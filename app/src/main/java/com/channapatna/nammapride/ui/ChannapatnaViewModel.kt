package com.channapatna.nammapride.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.channapatna.nammapride.data.Artisan
import com.channapatna.nammapride.data.ChannapatnaRepository
import com.channapatna.nammapride.data.EducationStep
import com.channapatna.nammapride.data.Toy
import com.channapatna.nammapride.data.VerifiedToy
import com.channapatna.nammapride.data.Workshop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AppLanguage(val code: String) {
    English("en"),
    Kannada("kn")
}

data class VerificationState(
    val toyId: String = "",
    val result: VerifiedToy? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)

data class AppContentState(
    val toys: List<Toy> = emptyList(),
    val artisans: List<Artisan> = emptyList(),
    val workshops: List<Workshop> = emptyList(),
    val educationSteps: List<EducationStep> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

data class AdminAuthState(
    val email: String = "",
    val password: String = "",
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null
)

data class AddArtisanState(
    val artisanId: String = "",
    val name: String = "",
    val nameKannada: String = "",
    val village: String = "",
    val experience: String = "",
    val specialization: String = "",
    val bio: String = "",
    val bioKannada: String = "",
    val workshopId: String = "",
    val isLoading: Boolean = false,
    val message: String? = null
)

data class AddWorkshopState(
    val workshopId: String = "",
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val timings: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val isLoading: Boolean = false,
    val message: String? = null
)

class ChannapatnaViewModel(
    private val repository: ChannapatnaRepository
) : ViewModel() {
    private val _contentState = MutableStateFlow(AppContentState())
    val contentState: StateFlow<AppContentState> = _contentState.asStateFlow()

    private val _verificationState = MutableStateFlow(VerificationState())
    val verificationState: StateFlow<VerificationState> = _verificationState.asStateFlow()

    private val _language = MutableStateFlow(AppLanguage.English)
    val language: StateFlow<AppLanguage> = _language.asStateFlow()

    private val _adminAuthState = MutableStateFlow(AdminAuthState(isSignedIn = repository.isAdminSignedIn()))
    val adminAuthState: StateFlow<AdminAuthState> = _adminAuthState.asStateFlow()

    private val _addArtisanState = MutableStateFlow(AddArtisanState())
    val addArtisanState: StateFlow<AddArtisanState> = _addArtisanState.asStateFlow()

    private val _addWorkshopState = MutableStateFlow(AddWorkshopState())
    val addWorkshopState: StateFlow<AddWorkshopState> = _addWorkshopState.asStateFlow()

    init {
        refreshContent()
    }

    fun refreshContent() {
        viewModelScope.launch {
            _contentState.value = _contentState.value.copy(isLoading = true, error = null)
            runCatching {
                AppContentState(
                    toys = repository.toys(),
                    artisans = repository.artisans(),
                    workshops = repository.workshops(),
                    educationSteps = repository.educationSteps(),
                    isLoading = false
                )
            }.onSuccess { state ->
                _contentState.value = state
            }.onFailure { error ->
                _contentState.value = _contentState.value.copy(
                    isLoading = false,
                    error = error.message ?: "Could not load app content"
                )
            }
        }
    }

    fun toggleLanguage() {
        _language.value = if (_language.value == AppLanguage.English) AppLanguage.Kannada else AppLanguage.English
    }

    fun updateToyId(value: String) {
        val digits = value.filter(Char::isDigit).take(6)
        _verificationState.value = _verificationState.value.copy(
            toyId = digits,
            error = null,
            result = null
        )
    }

    fun verifyToy() {
        val toyId = _verificationState.value.toyId
        if (toyId.length != 6) {
            _verificationState.value = _verificationState.value.copy(error = "Enter the 6-digit Toy ID")
            return
        }

        viewModelScope.launch {
            _verificationState.value = _verificationState.value.copy(isLoading = true, error = null, result = null)
            val result = runCatching { repository.verifyToy(toyId, _language.value.code) }.getOrNull()
            _verificationState.value = if (result == null) {
                _verificationState.value.copy(
                    error = "No GI-tagged toy found for ID $toyId",
                    result = null,
                    isLoading = false
                )
            } else {
                _verificationState.value.copy(error = null, result = result, isLoading = false)
            }
        }
    }

    fun updateAdminEmail(value: String) {
        _adminAuthState.value = _adminAuthState.value.copy(email = value, message = null)
    }

    fun updateAdminPassword(value: String) {
        _adminAuthState.value = _adminAuthState.value.copy(password = value, message = null)
    }

    fun adminSignIn() {
        val state = _adminAuthState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _adminAuthState.value = state.copy(message = "Enter email and password")
            return
        }
        viewModelScope.launch {
            _adminAuthState.value = state.copy(isLoading = true, message = null)
            val result = repository.adminSignIn(state.email, state.password)
            _adminAuthState.value = if (result.isSuccess) {
                _adminAuthState.value.copy(isLoading = false, isSignedIn = true, message = "Admin signed in")
            } else {
                _adminAuthState.value.copy(
                    isLoading = false,
                    isSignedIn = false,
                    message = result.exceptionOrNull()?.message ?: "Admin login failed"
                )
            }
        }
    }

    fun adminSignOut() {
        repository.adminSignOut()
        _adminAuthState.value = AdminAuthState()
    }

    fun updateArtisanDraft(update: AddArtisanState.() -> AddArtisanState) {
        _addArtisanState.value = _addArtisanState.value.update()
    }

    fun updateWorkshopDraft(update: AddWorkshopState.() -> AddWorkshopState) {
        _addWorkshopState.value = _addWorkshopState.value.update()
    }

    fun submitArtisan() {
        if (!_adminAuthState.value.isSignedIn) {
            _addArtisanState.value = _addArtisanState.value.copy(message = "Sign in as admin first")
            return
        }
        val draft = _addArtisanState.value
        if (draft.artisanId.isBlank() || draft.name.isBlank() || draft.workshopId.isBlank()) {
            _addArtisanState.value = draft.copy(message = "Artisan ID, Name and Workshop ID are required")
            return
        }
        viewModelScope.launch {
            _addArtisanState.value = draft.copy(isLoading = true, message = null)
            val result = repository.addArtisan(
                Artisan(
                    artisanId = draft.artisanId.trim(),
                    name = draft.name.trim(),
                    nameKannada = draft.nameKannada.trim(),
                    village = draft.village.trim(),
                    experience = draft.experience.toIntOrNull() ?: 0,
                    specialization = draft.specialization.trim(),
                    bio = draft.bio.trim(),
                    bioKannada = draft.bioKannada.trim(),
                    workshopId = draft.workshopId.trim()
                )
            )
            _addArtisanState.value = if (result.isSuccess) {
                AddArtisanState(message = "Artisan added successfully")
            } else {
                draft.copy(
                    isLoading = false,
                    message = result.exceptionOrNull()?.message ?: "Failed to add artisan"
                )
            }
            if (result.isSuccess) {
                refreshContent()
            }
        }
    }

    fun submitWorkshop() {
        if (!_adminAuthState.value.isSignedIn) {
            _addWorkshopState.value = _addWorkshopState.value.copy(message = "Sign in as admin first")
            return
        }
        val draft = _addWorkshopState.value
        if (draft.workshopId.isBlank() || draft.name.isBlank() || draft.address.isBlank()) {
            _addWorkshopState.value = draft.copy(message = "Workshop ID, Name and Address are required")
            return
        }
        if (draft.latitude.isBlank() || draft.longitude.isBlank()) {
            _addWorkshopState.value = draft.copy(message = "Latitude and Longitude are required for map location")
            return
        }
        viewModelScope.launch {
            _addWorkshopState.value = draft.copy(isLoading = true, message = null)
            val result = repository.addWorkshop(
                Workshop(
                    workshopId = draft.workshopId.trim(),
                    name = draft.name.trim(),
                    address = draft.address.trim(),
                    phone = draft.phone.trim(),
                    timings = draft.timings.trim(),
                    latitude = draft.latitude.toDoubleOrNull() ?: 0.0,
                    longitude = draft.longitude.toDoubleOrNull() ?: 0.0
                )
            )
            _addWorkshopState.value = if (result.isSuccess) {
                AddWorkshopState(message = "Workshop added successfully")
            } else {
                draft.copy(
                    isLoading = false,
                    message = result.exceptionOrNull()?.message ?: "Failed to add workshop"
                )
            }
            if (result.isSuccess) {
                refreshContent()
            }
        }
    }
}

class ChannapatnaViewModelFactory(
    private val repository: ChannapatnaRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChannapatnaViewModel(repository) as T
    }
}
