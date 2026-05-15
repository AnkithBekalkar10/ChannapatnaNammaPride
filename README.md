Channapatna - Namma Pride
Complete Standard Operating Procedure (SOP) for Development
Android App Development Using Kotlin, Jetpack Compose & Firebase
DOCUMENT VERSION CONTROL
Document Version: 2.0 Final
Project Name: Channapatna - Namma Pride
Platform: Android (Kotlin + Jetpack Compose)
Backend: Firebase (Firestore + Storage + Gemini AI)
Target Delivery: Production-Ready MVP
Intended Audience: Development Team / AI Code Generator (AntiGravity)
TABLE OF CONTENTS
Executive Summary
Project Architecture Overview
Technology Stack Specifications
Firebase Backend Structure
Screen-by-Screen Implementation Guide
Screen Flow & Navigation Architecture
UI/UX Design System
Feature Implementation Roadmap
GenAI Integration Protocol
Localization Implementation
Testing & Quality Assurance
Deployment Checklist
1. EXECUTIVE SUMMARY
1.1 Project Vision
Channapatna - Namma Pride is a heritage technology application that transforms wooden toy authentication into an emotional storytelling experience. Each GI-tagged Channapatna toy gets a digital identity that connects buyers to the artisan who crafted it.

1.2 Core Problem Solved
Counterfeit Prevention: 6-digit ID verification system
Artisan Invisibility: Profile cards with photos, names, and AI-generated stories
Cultural Erosion: Educational content about 200+ year craft heritage
1.3 Success Metrics
100% accurate Toy ID verification
<2 second Firebase response time
5+ workshop locations on map
Full Kannada localization
Vibrant, toy-like UI scoring 4/5+ on playfulness
2. PROJECT ARCHITECTURE OVERVIEW
2.1 High-Level Architecture
text

┌─────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                       │
│              (Jetpack Compose UI Components)                 │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐  │
│  │  Home    │  Verify  │   Map    │ Catalog  │ Education│  │
│  │  Screen  │  Screen  │  Screen  │  Screen  │  Screen  │  │
│  └──────────┴──────────┴──────────┴──────────┴──────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    BUSINESS LOGIC LAYER                      │
│                   (ViewModels + UseCases)                    │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  - ToyVerificationViewModel                          │  │
│  │  - ArtisanProfileViewModel                           │  │
│  │  - MapViewModel                                      │  │
│  │  - CatalogViewModel                                  │  │
│  │  - EducationViewModel                                │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                      DATA LAYER                              │
│                    (Repository Pattern)                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  - ToyRepository                                      │  │
│  │  - ArtisanRepository                                  │  │
│  │  - WorkshopRepository                                 │  │
│  │  - GeminiAIRepository                                 │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    FIREBASE BACKEND                          │
│  ┌──────────────┬──────────────┬──────────────────────┐   │
│  │  Firestore   │   Storage    │   Gemini AI API      │   │
│  │  Database    │   (Images)   │   (Story Gen)        │   │
│  └──────────────┴──────────────┴──────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
2.2 MVVM Architecture Pattern
Kotlin

// Pattern Structure
Screen (Composable)
    ↓
ViewModel (State Management)
    ↓
Repository (Data Abstraction)
    ↓
Data Source (Firebase / Gemini AI)
3. TECHNOLOGY STACK SPECIFICATIONS
3.1 Frontend Stack
Component	Technology	Version	Purpose
Language	Kotlin	1.9+	Primary development language
UI Framework	Jetpack Compose	1.5+	Declarative UI
UI Components	Material Design 3	Latest	Design system
Navigation	Compose Navigation	2.7+	Screen routing
State Management	ViewModel + StateFlow	Latest	Reactive state
Dependency Injection	Hilt	2.48+	DI framework
Image Loading	Coil	2.5+	Async image loading
Localization	Android Resources	Native	Multi-language support
3.2 Backend Stack
Component	Technology	Purpose
Database	Firebase Firestore	NoSQL cloud database
Storage	Firebase Storage	Image/video hosting
AI Engine	Google Gemini API	Story generation
Maps	Google Maps SDK	Workshop locations
Offline	Firebase Offline Persistence	Local caching
3.3 Gradle Dependencies (build.gradle.kts)
Kotlin

dependencies {
    // Kotlin & Core
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    
    // Gemini AI
    implementation("com.google.ai.client.generativeai:generativeai:0.1.2")
    
    // Google Maps
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    
    // Image Loading
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}
4. FIREBASE BACKEND STRUCTURE
4.1 Firestore Database Schema
Collection: toys
JSON

{
  "toyId": "123456",
  "artisanId": "ART001",
  "category": "Rocking Horse",
  "name": "Rainbow Galloper",
  "creationDate": "2025-01-15",
  "imageUrl": "gs://bucket/toys/toy_123456.jpg",
  "price": 1200,
  "verified": true,
  "giTagNumber": "GI-2005-00063"
}
Indexes Required:

Single Field: toyId (Ascending)
Composite: category (Ascending) + verified (Descending)
Collection: artisans
JSON

{
  "artisanId": "ART001",
  "name": "Rangappa K.M.",
  "nameKannada": "ರಂಗಪ್ಪ ಕೆ.ಎಂ.",
  "photoUrl": "gs://bucket/artisans/art001.jpg",
  "village": "Channapatna",
  "experience": 35,
  "bio": "Third generation toy maker specializing in lacquer finishing",
  "bioKannada": "ಮೂರನೇ ಪೀಳಿಗೆಯ ಆಟಿಕೆ ತಯಾರಕ",
  "workshopId": "WS001",
  "specialization": "Rocking Horses"
}
Collection: workshops
JSON

{
  "workshopId": "WS001",
  "name": "Rangappa's Traditional Toys",
  "nameKannada": "ರಂಗಪ್ಪನ ಸಾಂಪ್ರದಾಯಿಕ ಆಟಿಕೆಗಳು",
  "address": "Market Road, Channapatna, Karnataka 571501",
  "latitude": 12.6518,
  "longitude": 77.2069,
  "phone": "+91-9876543210",
  "timings": "9:00 AM - 6:00 PM",
  "artisanIds": ["ART001", "ART002"]
}
Collection: educational_content
JSON

{
  "contentId": "EDU001",
  "title": "The Story of Hale Wood",
  "titleKannada": "ಹಳೆ ಮರದ ಕಥೆ",
  "type": "process_step",
  "stepNumber": 1,
  "description": "Indian Rosewood (Hale Wood) is sustainably sourced...",
  "descriptionKannada": "ಭಾರತೀಯ ರೋಸ್‌ವುಡ್...",
  "imageUrl": "gs://bucket/education/wood_selection.jpg",
  "videoThumbnail": "gs://bucket/education/thumb_wood.jpg"
}
4.2 Firebase Storage Structure
text

gs://channapatna-pride.appspot.com/
├── artisans/
│   ├── art001.jpg
│   ├── art002.jpg
│   └── art003.jpg
├── toys/
│   ├── toy_123456.jpg
│   ├── toy_234567.jpg
│   └── toy_345678.jpg
├── education/
│   ├── wood_selection.jpg
│   ├── lathe_turning.jpg
│   ├── lac_application.jpg
│   └── video_thumbs/
│       ├── thumb_wood.jpg
│       └── thumb_lac.jpg
└── workshops/
    ├── workshop_exterior_ws001.jpg
    └── workshop_interior_ws001.jpg
4.3 Firebase Security Rules
JavaScript

rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    
    // Public read access for toys
    match /toys/{toyId} {
      allow read: if true;
      allow write: if false; // Only admin via Firebase Console
    }
    
    // Public read access for artisans
    match /artisans/{artisanId} {
      allow read: if true;
      allow write: if false;
    }
    
    // Public read access for workshops
    match /workshops/{workshopId} {
      allow read: if true;
      allow write: if false;
    }
    
    // Public read for educational content
    match /educational_content/{contentId} {
      allow read: if true;
      allow write: if false;
    }
  }
}
4.4 Sample Data Population Script
Kotlin

// Run once to populate initial data
class FirebaseDataSeeder {
    private val db = Firebase.firestore
    
    suspend fun seedDatabase() {
        seedArtisans()
        seedWorkshops()
        seedToys()
        seedEducation()
    }
    
    private suspend fun seedArtisans() {
        val artisans = listOf(
            hashMapOf(
                "artisanId" to "ART001",
                "name" to "Rangappa K.M.",
                "nameKannada" to "ರಂಗಪ್ಪ ಕೆ.ಎಂ.",
                "photoUrl" to "gs://bucket/artisans/art001.jpg",
                "village" to "Channapatna",
                "experience" to 35,
                "bio" to "Third generation master craftsman specializing in rocking horses",
                "bioKannada" to "ಮೂರನೇ ಪೀಳಿಗೆಯ ಮಾಸ್ಟರ್ ಕರಕುಶಲಿ",
                "workshopId" to "WS001",
                "specialization" to "Rocking Horses"
            ),
            // Add 4 more artisans...
        )
        
        artisans.forEach { artisan ->
            db.collection("artisans").add(artisan).await()
        }
    }
    
    private suspend fun seedWorkshops() {
        val workshops = listOf(
            hashMapOf(
                "workshopId" to "WS001",
                "name" to "Rangappa's Traditional Toys",
                "nameKannada" to "ರಂಗಪ್ಪನ ಸಾಂಪ್ರದಾಯಿಕ ಆಟಿಕೆಗಳು",
                "latitude" to 12.6518,
                "longitude" to 77.2069,
                "phone" to "+91-9876543210",
                "timings" to "9:00 AM - 6:00 PM"
            ),
            // Add 4 more workshops...
        )
        
        workshops.forEach { workshop ->
            db.collection("workshops").add(workshop).await()
        }
    }
}
5. SCREEN-BY-SCREEN IMPLEMENTATION GUIDE
5.1 SCREEN 1: HOME SCREEN (The Gateway)
Purpose
Landing page that welcomes users and drives them toward toy verification.

UI Composition
text

┌─────────────────────────────────────────┐
│         [StatusBar - Green]             │
├─────────────────────────────────────────┤
│                                         │
│     🎨 [ANIMATED LOGO]                  │
│     Channapatna - Namma Pride           │
│     ಚನ್ನಪಟ್ಟಣ - ನಮ್ಮ ಹೆಮ್ಮೆ              │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │  🔍 VERIFY MY TOY               │   │
│  │  ನನ್ನ ಆಟಿಕೆಯನ್ನು ಪರಿಶೀಲಿಸಿ          │   │
│  └─────────────────────────────────┘   │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │  📍 MEET THE MAKERS             │   │
│  │  ತಯಾರಕರನ್ನು ಭೇಟಿ ಮಾಡಿ             │   │
│  └─────────────────────────────────┘   │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │  🎪 TOY CATALOG                 │   │
│  │  ಆಟಿಕೆ ಪಟ್ಟಿ                       │   │
│  └─────────────────────────────────┘   │
│                                         │
│  ┌─────────────────────────────────┐   │
│  │  📚 HOW IT'S MADE               │   │
│  │  ಅದನ್ನು ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ        │   │
│  └─────────────────────────────────┘   │
│                                         │
│           [🌐 Language Toggle]          │
└─────────────────────────────────────────┘
Implementation Code
Kotlin

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentLanguage = remember { mutableStateOf("en") }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50), // Green
                        Color(0xFFFFEB3B)  // Yellow
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        
        // Animated Logo
        AnimatedLogo()
        
        // App Title
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.app_name_kannada),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
        
        // Navigation Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeNavigationCard(
                icon = Icons.Default.Search,
                title = stringResource(R.string.verify_toy),
                subtitle = stringResource(R.string.verify_toy_kannada),
                backgroundColor = Color(0xFFFF5722),
                onClick = { navController.navigate("verify") }
            )
            
            HomeNavigationCard(
                icon = Icons.Default.Place,
                title = stringResource(R.string.meet_makers),
                subtitle = stringResource(R.string.meet_makers_kannada),
                backgroundColor = Color(0xFF2196F3),
                onClick = { navController.navigate("map") }
            )
            
            HomeNavigationCard(
                icon = Icons.Default.ShoppingBag,
                title = stringResource(R.string.toy_catalog),
                subtitle = stringResource(R.string.toy_catalog_kannada),
                backgroundColor = Color(0xFF9C27B0),
                onClick = { navController.navigate("catalog") }
            )
            
            HomeNavigationCard(
                icon = Icons.Default.Info,
                title = stringResource(R.string.how_made),
                subtitle = stringResource(R.string.how_made_kannada),
                backgroundColor = Color(0xFFFF9800),
                onClick = { navController.navigate("education") }
            )
        }
        
        // Language Toggle
        LanguageToggleButton(
            currentLanguage = currentLanguage.value,
            onLanguageChange = { lang ->
                currentLanguage.value = lang
                setAppLocale(context, lang)
            }
        )
    }
}

@Composable
fun HomeNavigationCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun AnimatedLogo() {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_animation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Image(
        painter = painterResource(id = R.drawable.logo_toy),
        contentDescription = "App Logo",
        modifier = Modifier
            .size(120.dp)
            .rotate(rotation)
    )
}

@Composable
fun LanguageToggleButton(
    currentLanguage: String,
    onLanguageChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextButton(
            onClick = { onLanguageChange("en") },
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (currentLanguage == "en") Color.White else Color.Transparent
            )
        ) {
            Text("English", color = Color.Black)
        }
        
        TextButton(
            onClick = { onLanguageChange("kn") },
            colors = ButtonDefaults.textButtonColors(
                containerColor = if (currentLanguage == "kn") Color.White else Color.Transparent
            )
        ) {
            Text("ಕನ್ನಡ", color = Color.Black)
        }
    }
}

fun setAppLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
}
Screen State Management
Kotlin

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _selectedLanguage = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()
    
    fun setLanguage(language: String) {
        _selectedLanguage.value = language
    }
}
5.2 SCREEN 2: VERIFICATION & ARTISAN PROFILE (The Core)
Purpose
Two-state screen: Input 6-digit Toy ID → Display artisan profile + AI story

UI Composition - State A (Input)
text

┌─────────────────────────────────────────┐
│     ← Back      Verify Toy              │
├─────────────────────────────────────────┤
│                                         │
│         🔍                              │
│    Enter Your Toy ID                    │
│    ನಿಮ್ಮ ಆಟಿಕೆ ID ನಮೂದಿಸಿ                │
│                                         │
│   ┌───────────────────────────────┐    │
│   │   [  1  ]  [  2  ]  [  3  ]   │    │
│   │   [  4  ]  [  5  ]  [  6  ]   │    │
│   └───────────────────────────────┘    │
│                                         │
│   ┌─────────────────────────────────┐  │
│   │      VERIFY NOW ✓               │  │
│   └─────────────────────────────────┘  │
│                                         │
│   📌 Toy ID is printed on the          │
│      bottom of your wooden toy          │
│                                         │
└─────────────────────────────────────────┘
UI Composition - State B (Result)
text

┌─────────────────────────────────────────┐
│     ← Back    Artisan Profile           │
├─────────────────────────────────────────┤
│                                         │
│   ┌─────────────────────────────────┐  │
│   │   [Artisan Photo - Circular]    │  │
│   │                                 │  │
│   │      Rangappa K.M.              │  │
│   │      ರಂಗಪ್ಪ ಕೆ.ಎಂ.                │  │
│   │                                 │  │
│   │   📍 Channapatna, Karnataka     │  │
│   │   ⭐ 35 Years Experience        │  │
│   │   🎨 Specialization: Horses    │  │
│   └─────────────────────────────────┘  │
│                                         │
│   ━━━ Story of the Wood ━━━            │
│                                         │
│   This beautiful rocking horse was     │
│   crafted by Rangappa, a third-        │
│   generation artisan who learned       │
│   the craft from his grandfather...    │
│                                         │
│   [Generated by AI ✨]                  │
│                                         │
│   ┌─────────────────────────────────┐  │
│   │   🎥 Watch Making Process       │  │
│   │   [Video Thumbnail]             │  │
│   └─────────────────────────────────┘  │
│                                         │
│   Toy ID: 123456  ✅ Verified          │
│   Created: Jan 15, 2025                │
│                                         │
│   ┌─────────────────────────────────┐  │
│   │   📜 DOWNLOAD CERTIFICATE       │  │
│   └─────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
Implementation Code
Kotlin

@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    
    when (uiState) {
        is VerificationUiState.Input -> {
            ToyIdInputScreen(
                onVerifyClick = { toyId ->
                    viewModel.verifyToy(toyId)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        is VerificationUiState.Loading -> {
            LoadingScreen()
        }
        is VerificationUiState.Success -> {
            ArtisanProfileScreen(
                artisan = (uiState as VerificationUiState.Success).artisan,
                toyInfo = (uiState as VerificationUiState.Success).toyInfo,
                aiStory = (uiState as VerificationUiState.Success).aiStory,
                onBackClick = { viewModel.resetState() }
            )
        }
        is VerificationUiState.Error -> {
            ErrorScreen(
                message = (uiState as VerificationUiState.Error).message,
                onRetry = { viewModel.resetState() }
            )
        }
    }
}

@Composable
fun ToyIdInputScreen(
    onVerifyClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var toyId by remember { mutableStateOf(List(6) { "" }) }
    val focusRequesters = remember { List(6) { FocusRequester() } }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF9C4)) // Light yellow
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = stringResource(R.string.verify_toy),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        // Icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = Color(0xFFFF5722)
        )
        
        // Title
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Enter Your Toy ID",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ನಿಮ್ಮ ಆಟಿಕೆ ID ನಮೂದಿಸಿ",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
        
        // 6-Digit Input
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            toyId.forEachIndexed { index, digit ->
                OutlinedTextField(
                    value = digit,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            val newToyId = toyId.toMutableList()
                            newToyId[index] = newValue
                            toyId = newToyId
                            
                            // Auto-focus next field
                            if (newValue.isNotEmpty() && index < 5) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .width(50.dp)
                        .focusRequester(focusRequesters[index]),
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = if (index == 5) ImeAction.Done else ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }
        }
        
        // Verify Button
        Button(
            onClick = {
                val fullId = toyId.joinToString("")
                if (fullId.length == 6) {
                    onVerifyClick(fullId)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722)
            ),
            enabled = toyId.all { it.isNotEmpty() },
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.verify_now),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Help Text
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD)
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF2196F3)
                )
                Text(
                    text = "Toy ID is printed on the bottom of your wooden toy",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ArtisanProfileScreen(
    artisan: Artisan,
    toyInfo: ToyInfo,
    aiStory: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE8F5E9), Color.White)
                )
            )
    ) {
        
        // Header
        TopAppBar(
            title = { Text("Artisan Profile") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF4CAF50)
            )
        )
        
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            
            // Artisan Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Profile Photo
                    AsyncImage(
                        model = artisan.photoUrl,
                        contentDescription = "Artisan Photo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(4.dp, Color(0xFF4CAF50), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Name
                    Text(
                        text = artisan.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = artisan.nameKannada,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    
                    // Details
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DetailChip(
                            icon = Icons.Default.Place,
                            text = artisan.village
                        )
                        DetailChip(
                            icon = Icons.Default.Star,
                            text = "${artisan.experience} Years"
                        )
                    }
                    
                    DetailChip(
                        icon = Icons.Default.Build,
                        text = "Specialization: ${artisan.specialization}"
                    )
                }
            }
            
            // AI-Generated Story
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF9C4)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "✨ Story of the Wood",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Text(
                        text = aiStory,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp
                    )
                    
                    Text(
                        text = "Generated by AI",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
            
            // Video Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Play video */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF2196F3)
                    )
                    Column {
                        Text(
                            text = "Watch Making Process",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See how ${artisan.name} crafts this toy",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
            
            // Verification Badge
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Toy ID: ${toyInfo.toyId}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Created: ${toyInfo.creationDate}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50)
                        )
                        Text(
                            text = "Verified",
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Download Certificate Button (Good to Have)
            OutlinedButton(
                onClick = { /* Generate certificate */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                border = BorderStroke(2.dp, Color(0xFF4CAF50)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Download Authenticity Certificate",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DetailChip(icon: ImageVector, text: String) {
    Surface(
        color = Color(0xFFE8F5E9),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
ViewModel & Business Logic
Kotlin

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val toyRepository: ToyRepository,
    private val artisanRepository: ArtisanRepository,
    private val geminiRepository: GeminiAIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<VerificationUiState>(VerificationUiState.Input)
    val uiState: StateFlow<VerificationUiState> = _uiState.asStateFlow()

    fun verifyToy(toyId: String) {
        viewModelScope.launch {
            _uiState.value = VerificationUiState.Loading
            
            try {
                // Step 1: Fetch toy info from Firebase
                val toyInfo = toyRepository.getToyById(toyId)
                
                if (toyInfo == null) {
                    _uiState.value = VerificationUiState.Error(
                        "Toy ID not found. Please check and try again."
                    )
                    return@launch
                }
                
                // Step 2: Fetch artisan profile
                val artisan = artisanRepository.getArtisanById(toyInfo.artisanId)
                
                if (artisan == null) {
                    _uiState.value = VerificationUiState.Error(
                        "Artisan information not available."
                    )
                    return@launch
                }
                
                // Step 3: Generate AI story
                val aiStory = geminiRepository.generateArtisanStory(
                    artisanName = artisan.name,
                    specialization = artisan.specialization,
                    toyCategory = toyInfo.category
                )
                
                _uiState.value = VerificationUiState.Success(
                    artisan = artisan,
                    toyInfo = toyInfo,
                    aiStory = aiStory
                )
                
            } catch (e: Exception) {
                _uiState.value = VerificationUiState.Error(
                    "Verification failed: ${e.message}"
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = VerificationUiState.Input
    }
}

sealed class VerificationUiState {
    object Input : VerificationUiState()
    object Loading : VerificationUiState()
    data class Success(
        val artisan: Artisan,
        val toyInfo: ToyInfo,
        val aiStory: String
    ) : VerificationUiState()
    data class Error(val message: String) : VerificationUiState()
}
Data Models
Kotlin

data class Artisan(
    val artisanId: String = "",
    val name: String = "",
    val nameKannada: String = "",
    val photoUrl: String = "",
    val village: String = "",
    val experience: Int = 0,
    val bio: String = "",
    val bioKannada: String = "",
    val workshopId: String = "",
    val specialization: String = ""
)

data class ToyInfo(
    val toyId: String = "",
    val artisanId: String = "",
    val category: String = "",
    val name: String = "",
    val creationDate: String = "",
    val imageUrl: String = "",
    val price: Int = 0,
    val verified: Boolean = false,
    val giTagNumber: String = ""
)
Repository Implementation
Kotlin

class ToyRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getToyById(toyId: String): ToyInfo? = withContext(Dispatchers.IO) {
        try {
            val document = firestore.collection("toys")
                .whereEqualTo("toyId", toyId)
                .get()
                .await()
                .documents
                .firstOrNull()
            
            document?.toObject(ToyInfo::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

class ArtisanRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getArtisanById(artisanId: String): Artisan? = withContext(Dispatchers.IO) {
        try {
            val document = firestore.collection("artisans")
                .whereEqualTo("artisanId", artisanId)
                .get()
                .await()
                .documents
                .firstOrNull()
            
            document?.toObject(Artisan::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
5.3 SCREEN 3: MEET THE MAKER (The Map)
Purpose
Display interactive Google Map with 5+ workshop locations

UI Composition
text

┌─────────────────────────────────────────┐
│     ← Back    Meet the Makers           │
├─────────────────────────────────────────┤
│                                         │
│   ┌─────────────────────────────────┐  │
│   │                                 │  │
│   │         [Google Map]            │  │
│   │                                 │  │
│   │    📍    📍    📍               │  │
│   │                                 │  │
│   │         📍    📍                │  │
│   │                                 │  │
│   │  [Centered on Channapatna]     │  │
│   │                                 │  │
│   └─────────────────────────────────┘  │
│                                         │
│   [When Pin Tapped]                    │
│   ┌─────────────────────────────────┐  │
│   │ 🏠 Rangappa's Traditional Toys  │  │
│   │                                 │  │
│   │ 📍 Market Road, Channapatna     │  │
│   │ 📞 +91-9876543210               │  │
│   │ ⏰ 9:00 AM - 6:00 PM            │  │
│   │                                 │  │
│   │ [Get Directions →]              │  │
│   └─────────────────────────────────┘  │
│                                         │
└─────────────────────────────────────────┘
Implementation Code
Kotlin

@Composable
fun MeetTheMakerScreen(
    viewModel: MapViewModel = hiltViewModel(),
    navController: NavController
) {
    val workshops by viewModel.workshops.collectAsState()
    val selectedWorkshop by viewModel.selectedWorkshop.collectAsState()
    
    val channapatnaLocation = LatLng(12.6518, 77.2069)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(channapatnaLocation, 13f)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meet the Makers") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    mapType = MapType.NORMAL,
                    isMyLocationEnabled = false
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    compassEnabled = true
                )
            ) {
                workshops.forEach { workshop ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(workshop.latitude, workshop.longitude)
                        ),
                        title = workshop.name,
                        snippet = workshop.address,
                        onClick = {
                            viewModel.selectWorkshop(workshop)
                            true
                        },
                        icon = BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN
                        )
                    )
                }
            }
            
            // Workshop Detail Card
            selectedWorkshop?.let { workshop ->
                WorkshopDetailCard(
                    workshop = workshop,
                    onDismiss = { viewModel.clearSelection() },
                    onGetDirections = { 
                        openGoogleMapsDirections(workshop)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun WorkshopDetailCard(
    workshop: Workshop,
    onDismiss: () -> Unit,
    onGetDirections: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workshop.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
            
            Text(
                text = workshop.nameKannada,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            
            Divider()
            
            DetailRow(
                icon = Icons.Default.Place,
                text = workshop.address
            )
            
            DetailRow(
                icon = Icons.Default.Phone,
                text = workshop.phone
            )
            
            DetailRow(
                icon = Icons.Default.Info,
                text = workshop.timings
            )
            
            Button(
                onClick = onGetDirections,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Get Directions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF2196F3),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

fun openGoogleMapsDirections(workshop: Workshop) {
    val uri = "google.navigation:q=${workshop.latitude},${workshop.longitude}"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    // Start activity with context
}
ViewModel
Kotlin

@HiltViewModel
class MapViewModel @Inject constructor(
    private val workshopRepository: WorkshopRepository
) : ViewModel() {

    private val _workshops = MutableStateFlow<List<Workshop>>(emptyList())
    val workshops: StateFlow<List<Workshop>> = _workshops.asStateFlow()

    private val _selectedWorkshop = MutableStateFlow<Workshop?>(null)
    val selectedWorkshop: StateFlow<Workshop?> = _selectedWorkshop.asStateFlow()

    init {
        loadWorkshops()
    }

    private fun loadWorkshops() {
        viewModelScope.launch {
            try {
                val fetchedWorkshops = workshopRepository.getAllWorkshops()
                _workshops.value = fetchedWorkshops
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun selectWorkshop(workshop: Workshop) {
        _selectedWorkshop.value = workshop
    }

    fun clearSelection() {
        _selectedWorkshop.value = null
    }
}
Data Model
Kotlin

data class Workshop(
    val workshopId: String = "",
    val name: String = "",
    val nameKannada: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val phone: String = "",
    val timings: String = "",
    val artisanIds: List<String> = emptyList()
)
Repository
Kotlin

class WorkshopRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getAllWorkshops(): List<Workshop> = withContext(Dispatchers.IO) {
        try {
            firestore.collection("workshops")
                .get()
                .await()
                .toObjects(Workshop::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
5.4 SCREEN 4: TOY CATALOG (The Gallery)
Purpose
Browse toys by category in a colorful grid layout

UI Composition
text

┌─────────────────────────────────────────┐
│     ← Back    Toy Catalog               │
├─────────────────────────────────────────┤
│                                         │
│   [🎠 All] [🐴 Horses] [🧩 Puzzles]     │
│   [🦁 Animals] [🎎 Dolls]                │
│                                         │
│   ┌─────────┬─────────┐                │
│   │  [IMG]  │  [IMG]  │                │
│   │         │         │                │
│   │ Rainbow │ Jumping │                │
│   │ Galloper│ Elephant│                │
│   │         │         │                │
│   │ Rangappa│ Lakshmi │                │
│   │ ₹1200   │ ₹800    │                │
│   └─────────┴─────────┘                │
│                                         │
│   ┌─────────┬─────────┐                │
│   │  [IMG]  │  [IMG]  │                │
│   │         │         │                │
│   │ Stacking│ Wooden  │                │
│   │ Rings   │ Puzzle  │                │
│   │         │         │                │
│   │ Kumar   │ Gowda   │                │
│   │ ₹500    │ ₹650    │                │
│   └─────────┴─────────┘                │
│                                         │
└─────────────────────────────────────────┘
Implementation Code
Kotlin

@Composable
fun ToyCatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    navController: NavController
) {
    val toys by viewModel.toys.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val categories = listOf("All", "Rocking Horses", "Puzzles", "Animal Figurines", "Traditional Dolls")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Toy Catalog") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF9C27B0)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF3E5F5)) // Light purple
        ) {
            
            // Category Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        category = category,
                        isSelected = category == selectedCategory,
                        onClick = { viewModel.selectCategory(category) }
                    )
                }
            }
            
            // Toy Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(toys) { toy ->
                    ToyCard(
                        toy = toy,
                        onClick = { /* Navigate to detail */ }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF9C27B0) else Color.White,
        border = BorderStroke(1.dp, Color(0xFF9C27B0))
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = if (isSelected) Color.White else Color(0xFF9C27B0),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ToyCard(
    toy: Toy,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Toy Image
            AsyncImage(
                model = toy.imageUrl,
                contentDescription = toy.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = toy.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "By ${toy.artisanName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "₹${toy.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
ViewModel
Kotlin

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val toyRepository: ToyRepository
) : ViewModel() {

    private val _allToys = MutableStateFlow<List<Toy>>(emptyList())
    private val _toys = MutableStateFlow<List<Toy>>(emptyList())
    val toys: StateFlow<List<Toy>> = _toys.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    init {
        loadToys()
    }

    private fun loadToys() {
        viewModelScope.launch {
            try {
                val fetchedToys = toyRepository.getAllToys()
                _allToys.value = fetchedToys
                _toys.value = fetchedToys
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _toys.value = if (category == "All") {
            _allToys.value
        } else {
            _allToys.value.filter { it.category == category }
        }
    }
}
Data Model
Kotlin

data class Toy(
    val toyId: String = "",
    val name: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val artisanName: String = "",
    val price: Int = 0
)
5.5 SCREEN 5: EDUCATION SCREEN (Heritage & Process)
Purpose
Educate users about Hale Wood and Lac dye process

UI Composition
text

┌─────────────────────────────────────────┐
│     ← Back    How It's Made             │
├─────────────────────────────────────────┤
│                                         │
│   [Scrollable Content]                  │
│                                         │
│   ━━━━ 200 Years of Heritage ━━━━      │
│                                         │
│   The Channapatna craft began in the   │
│   18th century when Tipu Sultan        │
│   invited Persian artisans to teach... │
│                                         │
│   ┌─────────────────────────────────┐  │
│   │   [Historical Image]            │  │
│   └─────────────────────────────────┘  │
│                                         │
│   ━━━━ The Making Process ━━━━         │
│                                         │
│   Step 1: Wood Selection               │
│   🌳 Hale Wood (Indian Rosewood)       │
│   [Image]                              │
│   Sustainable sourced from...          │
│                                         │
│   Step 2: Lathe Turning                │
│   🎯 Precision Shaping                 │
│   [Video Thumbnail] ▶                  │
│   The wood is mounted on a lathe...    │
│                                         │
│   Step 3: Lac Application              │
│   🎨 Natural Vegetable Dyes            │
│   [Image]                              │
│   Lac sticks are pressed against...    │
│                                         │
│   Step 4: Finishing & Polishing        │
│   ✨ Mirror Finish                      │
│   [Image]                              │
│   Final smoothing and buffing...       │
│                                         │
│   ━━━━ GI Tag Protection ━━━━          │
│   GI-2005-00063                        │
│                                         │
└─────────────────────────────────────────┘
Implementation Code
Kotlin

@Composable
fun EducationScreen(
    viewModel: EducationViewModel = hiltViewModel(),
    navController: NavController
) {
    val educationalContent by viewModel.content.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("How It's Made") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF9800)
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFF3E0)),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Heritage Section
            item {
                SectionHeader(
                    title = "200 Years of Heritage",
                    icon = "🏛️"
                )
            }
            
            item {
                Text(
                    text = "The Channapatna craft began in the 18th century when Tipu Sultan invited Persian artisans to teach local craftsmen the art of wooden toy making. Today, it stands as a proud Geographical Indication (GI) tagged heritage craft.",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 24.sp
                )
            }
            
            item {
                AsyncImage(
                    model = "gs://bucket/education/heritage.jpg",
                    contentDescription = "Heritage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            
            // Making Process
            item {
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                SectionHeader(
                    title = "The Making Process",
                    icon = "⚙️"
                )
            }
            
            // Process Steps
            items(educationalContent) { step ->
                ProcessStepCard(step = step)
            }
            
            // GI Tag
            item {
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "🏆 GI Tag Protected",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "GI-2005-00063",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Geographical Indication certification ensures authenticity and protects this heritage craft from counterfeits.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProcessStepCard(step: EducationalContent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${step.stepNumber}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                
                Column {
                    Text(
                        text = step.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = step.titleKannada,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            
            // Image or Video
            if (step.type == "video") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.1f))
                        .clickable { /* Play video */ },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = step.videoThumbnail,
                        contentDescription = "Video Thumbnail",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                }
            } else {
                AsyncImage(
                    model = step.imageUrl,
                    contentDescription = step.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            
            Text(
                text = step.description,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 22.sp
            )
            
            Text(
                text = step.descriptionKannada,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                lineHeight = 20.sp
            )
        }
    }
}
ViewModel
Kotlin

@HiltViewModel
class EducationViewModel @Inject constructor(
    private val educationRepository: EducationRepository
) : ViewModel() {

    private val _content = MutableStateFlow<List<EducationalContent>>(emptyList())
    val content: StateFlow<List<EducationalContent>> = _content.asStateFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            try {
                val fetchedContent = educationRepository.getAllContent()
                _content.value = fetchedContent.sortedBy { it.stepNumber }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
Data Model
Kotlin

data class EducationalContent(
    val contentId: String = "",
    val title: String = "",
    val titleKannada: String = "",
    val type: String = "", // "image" or "video"
    val stepNumber: Int = 0,
    val description: String = "",
    val descriptionKannada: String = "",
    val imageUrl: String = "",
    val videoThumbnail: String = ""
)
6. SCREEN FLOW & NAVIGATION ARCHITECTURE
6.1 Navigation Graph
Kotlin

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "home"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        
        composable("verify") {
            VerificationScreen(navController = navController)
        }
        
        composable("map") {
            MeetTheMakerScreen(navController = navController)
        }
        
        composable("catalog") {
            ToyCatalogScreen(navController = navController)
        }
        
        composable("education") {
            EducationScreen(navController = navController)
        }
    }
}
6.2 Screen Flow Diagram
text

                    ┌──────────────┐
                    │  Splash      │
                    │  Screen      │
                    │  (Optional)  │
                    └──────┬───────┘
                           │
                           ▼
                    ┌──────────────┐
                    │              │
                    │  HOME        │
                    │  SCREEN      │
                    │              │
                    └──────┬───────┘
                           │
         ┌─────────────────┼─────────────────┬──────────────┐
         │                 │                 │              │
         ▼                 ▼                 ▼              ▼
   ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
   │ VERIFY   │    │   MAP    │    │ CATALOG  │    │EDUCATION │
   │  TOY     │    │  SCREEN  │    │ SCREEN   │    │ SCREEN   │
   └────┬─────┘    └──────────┘    └──────────┘    └──────────┘
        │
        ▼
   ┌──────────┐
   │ ARTISAN  │
   │ PROFILE  │
   └──────────┘
6.3 User Journey Map
Journey 1: Toy Verification (Primary Flow)
User opens app → Home Screen
Taps "Verify My Toy" → Verification Input Screen
Enters 6-digit ID → Firebase query initiated
ID validated → Artisan Profile Screen displayed
Views artisan details + AI story
(Optional) Downloads certificate
Journey 2: Exploration Flow
User opens app → Home Screen
Taps "Meet the Makers" → Map Screen
Sees workshop pins → Taps a pin
Views workshop details → Gets directions
Journey 3: Educational Flow
User opens app → Home Screen
Taps "How It's Made" → Education Screen
Scrolls through process steps
Watches embedded video thumbnails
Journey 4: Catalog Browsing
User opens app → Home Screen
Taps "Toy Catalog" → Catalog Screen
Filters by category
Views toy details
7. UI/UX DESIGN SYSTEM
7.1 Color Palette
Kotlin

object ChannapatnaColors {
    val Primary = Color(0xFF4CAF50)        // Green - Main theme
    val PrimaryVariant = Color(0xFF388E3C) // Dark Green
    val Secondary = Color(0xFFFFEB3B)      // Yellow - Accent
    val SecondaryVariant = Color(0xFFFBC02D)
    
    val VerifyRed = Color(0xFFFF5722)      // Verification CTA
    val MapBlue = Color(0xFF2196F3)        // Map/Location
    val CatalogPurple = Color(0xFF9C27B0)  // Catalog theme
    val EducationOrange = Color(0xFFFF9800) // Education theme
    
    val Success = Color(0xFF4CAF50)
    val Error = Color(0xFFF44336)
    val Warning = Color(0xFFFF9800)
    
    val Background = Color(0xFFFFFDE7)     // Light yellow background
    val Surface = Color.White
    val OnPrimary = Color.White
    val OnBackground = Color(0xFF212121)
}
7.2 Typography
Kotlin

val ChannapatnaTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
)
7.3 Shape System
Kotlin

val ChannapatnaShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)
7.4 Theme Configuration
Kotlin

@Composable
fun ChannapatnaPrideTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = ChannapatnaColors.Primary,
            secondary = ChannapatnaColors.Secondary,
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E)
        )
    } else {
        lightColorScheme(
            primary = ChannapatnaColors.Primary,
            secondary = ChannapatnaColors.Secondary,
            background = ChannapatnaColors.Background,
            surface = ChannapatnaColors.Surface
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ChannapatnaTypography,
        shapes = ChannapatnaShapes,
        content = content
    )
}
8. FEATURE IMPLEMENTATION ROADMAP
Phase 1: Core MVP (Must-Have Features)
Week 1: Project Setup & Firebase Integration
 Initialize Android Studio project with Kotlin + Jetpack Compose
 Setup Firebase project and add google-services.json
 Configure Firestore, Storage, and Security Rules
 Setup Hilt dependency injection
 Create MVVM architecture scaffolding
 Implement Navigation component
Week 2: Verification & Artisan Profile
 Design and implement Toy ID input UI
 Create Firebase query logic for toy verification
 Build Artisan Profile Card UI
 Integrate Gemini AI for story generation
 Add error handling and loading states
 Test with sample data
Week 3: Map & Catalog
 Integrate Google Maps SDK
 Create workshop marker UI and InfoWindows
 Implement Toy Catalog grid layout
 Add category filtering
 Seed Firebase with 5+ workshops and 9+ toys
Week 4: Education Screen & Localization
 Build educational content UI
 Add process step cards with images
 Implement full Kannada translation (strings.xml)
 Test language toggle functionality
 Polish UI with vibrant colors and animations
Week 5: Testing & Deployment
 End-to-end testing of all flows
 Performance optimization
 Generate signed APK
 Deploy to Firebase App Distribution (internal testing)
Phase 2: Good-to-Have Features (Post-MVP)
Priority 1: Offline Mode
 Enable Firebase offline persistence
 Cache last 10 verified toys
 Add offline indicator badges
Priority 2: Digital Certificate
 Generate PNG certificate with toy ID and artisan details
 Add share functionality (WhatsApp, Email)
Priority 3: History Timeline
 Create illustrated 200-year timeline
 Add scroll animation effects
Priority 4: QR Code Scanner
 Integrate ML Kit for QR scanning
 Alternative to manual ID entry
9. GENAI INTEGRATION PROTOCOL
9.1 Gemini API Setup
Add Dependency
gradle

implementation("com.google.ai.client.generativeai:generativeai:0.1.2")
API Key Configuration
Kotlin

// local.properties (NOT committed to Git)
GEMINI_API_KEY=your_api_key_here
Kotlin

// BuildConfig access
val apiKey = BuildConfig.GEMINI_API_KEY
9.2 Repository Implementation
Kotlin

class GeminiAIRepository @Inject constructor() {
    
    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey
    )

    suspend fun generateArtisanStory(
        artisanName: String,
        specialization: String,
        toyCategory: String
    ): String = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(artisanName, specialization, toyCategory)
            
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Story generation failed. Please try again."
            
        } catch (e: Exception) {
            "This ${toyCategory.lowercase()} was lovingly handcrafted by $artisanName, a master artisan specializing in $specialization using traditional Channapatna techniques passed down through generations."
        }
    }

    private fun buildPrompt(
        artisanName: String,
        specialization: String,
        toyCategory: String
    ): String {
        return """
            You are a storytelling assistant for Channapatna wooden toys, a 200-year-old GI-tagged craft from Karnataka, India.
            
            Write a warm, emotional, 100-word story about a wooden toy with these details:
            - Artisan Name: $artisanName
            - Specialization: $specialization
            - Toy Type: $toyCategory
            
            Focus on:
            - The artisan's personal connection to the craft
            - The use of Hale Wood (Indian Rosewood) and natural Lac dyes
            - The heritage and pride of Channapatna craftsmanship
            - Why this toy is special and unique
            
            Tone: Warm, proud, educational, heartfelt
            Length: Exactly 100 words
            
            Do NOT include markdown formatting or bullet points. Write in flowing prose.
        """.trimIndent()
    }
}
9.3 Usage in ViewModel
Kotlin

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val toyRepository: ToyRepository,
    private val artisanRepository: ArtisanRepository,
    private val geminiRepository: GeminiAIRepository
) : ViewModel() {

    fun verifyToy(toyId: String) {
        viewModelScope.launch {
            _uiState.value = VerificationUiState.Loading
            
            val toyInfo = toyRepository.getToyById(toyId) ?: return@launch
            val artisan = artisanRepository.getArtisanById(toyInfo.artisanId) ?: return@launch
            
            // Generate AI story
            val aiStory = geminiRepository.generateArtisanStory(
                artisanName = artisan.name,
                specialization = artisan.specialization,
                toyCategory = toyInfo.category
            )
            
            _uiState.value = VerificationUiState.Success(
                artisan = artisan,
                toyInfo = toyInfo,
                aiStory = aiStory
            )
        }
    }
}
9.4 Sample AI-Generated Output
Input:

Artisan: Rangappa K.M.
Specialization: Rocking Horses
Toy Category: Rocking Horse
Generated Story:

"This vibrant rocking horse was lovingly shaped by Rangappa K.M., a third-generation master craftsman from Channapatna. Using sustainable Hale Wood and natural lac dyes passed down through his family, Rangappa brings 35 years of passion to every curve and color. Each toy is a piece of Karnataka's 200-year heritage, certified with the GI tag that guarantees authenticity. When you hold this toy, you hold a story of tradition, pride, and the gentle hands that turned raw wood into childhood joy."

10. LOCALIZATION IMPLEMENTATION
10.1 String Resources Structure
File: res/values/strings.xml (English)
XML

<resources>
    <string name="app_name">Channapatna - Namma Pride</string>
    <string name="app_name_kannada">ಚನ್ನಪಟ್ಟಣ - ನಮ್ಮ ಹೆಮ್ಮೆ</string>
    
    <!-- Home Screen -->
    <string name="verify_toy">Verify My Toy</string>
    <string name="verify_toy_kannada">ನನ್ನ ಆಟಿಕೆಯನ್ನು ಪರಿಶೀಲಿಸಿ</string>
    <string name="meet_makers">Meet the Makers</string>
    <string name="meet_makers_kannada">ತಯಾರಕರನ್ನು ಭೇಟಿ ಮಾಡಿ</string>
    <string name="toy_catalog">Toy Catalog</string>
    <string name="toy_catalog_kannada">ಆಟಿಕೆ ಪಟ್ಟಿ</string>
    <string name="how_made">How It\'s Made</string>
    <string name="how_made_kannada">ಅದನ್ನು ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ</string>
    
    <!-- Verification Screen -->
    <string name="enter_toy_id">Enter Your Toy ID</string>
    <string name="enter_toy_id_kannada">ನಿಮ್ಮ ಆಟಿಕೆ ID ನಮೂದಿಸಿ</string>
    <string name="verify_now">Verify Now</string>
    <string name="toy_id_help">Toy ID is printed on the bottom of your wooden toy</string>
    <string name="verified">Verified</string>
    <string name="error_invalid_id">Invalid Toy ID. Please check and try again.</string>
    
    <!-- Artisan Profile -->
    <string name="artisan_profile">Artisan Profile</string>
    <string name="story_of_wood">Story of the Wood</string>
    <string name="watch_making_process">Watch Making Process</string>
    <string name="download_certificate">Download Authenticity Certificate</string>
    <string name="created_on">Created on: %s</string>
    
    <!-- Map Screen -->
    <string name="get_directions">Get Directions</string>
    <string name="workshop_timings">Workshop Timings</string>
    
    <!-- Catalog Screen -->
    <string name="all_toys">All</string>
    <string name="rocking_horses">Rocking Horses</string>
    <string name="puzzles">Puzzles</string>
    <string name="animal_figurines">Animal Figurines</string>
    <string name="traditional_dolls">Traditional Dolls</string>
    
    <!-- Education Screen -->
    <string name="heritage_title">200 Years of Heritage</string>
    <string name="making_process">The Making Process</string>
    <string name="gi_tag_protected">GI Tag Protected</string>
    <string name="step_wood_selection">Wood Selection</string>
    <string name="step_lathe_turning">Lathe Turning</string>
    <string name="step_lac_application">Lac Application</string>
    <string name="step_finishing">Finishing &amp; Polishing</string>
</resources>
File: res/values-kn/strings.xml (Kannada)
XML

<resources>
    <string name="app_name">ಚನ್ನಪಟ್ಟಣ - ನಮ್ಮ ಹೆಮ್ಮೆ</string>
    <string name="app_name_kannada">ಚನ್ನಪಟ್ಟಣ - ನಮ್ಮ ಹೆಮ್ಮೆ</string>
    
    <!-- Home Screen -->
    <string name="verify_toy">ನನ್ನ ಆಟಿಕೆಯನ್ನು ಪರಿಶೀಲಿಸಿ</string>
    <string name="verify_toy_kannada">ನನ್ನ ಆಟಿಕೆಯನ್ನು ಪರಿಶೀಲಿಸಿ</string>
    <string name="meet_makers">ತಯಾರಕರನ್ನು ಭೇಟಿ ಮಾಡಿ</string>
    <string name="meet_makers_kannada">ತಯಾರಕರನ್ನು ಭೇಟಿ ಮಾಡಿ</string>
    <string name="toy_catalog">ಆಟಿಕೆ ಪಟ್ಟಿ</string>
    <string name="toy_catalog_kannada">ಆಟಿಕೆ ಪಟ್ಟಿ</string>
    <string name="how_made">ಅದನ್ನು ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ</string>
    <string name="how_made_kannada">ಅದನ್ನು ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ</string>
    
    <!-- Verification Screen -->
    <string name="enter_toy_id">ನಿಮ್ಮ ಆಟಿಕೆ ID ನಮೂದಿಸಿ</string>
    <string name="enter_toy_id_kannada">ನಿಮ್ಮ ಆಟಿಕೆ ID ನಮೂದಿಸಿ</string>
    <string name="verify_now">ಈಗ ಪರಿಶೀಲಿಸಿ</string>
    <string name="toy_id_help">ಆಟಿಕೆ ID ನಿಮ್ಮ ಮರದ ಆಟಿಕೆಯ ಕೆಳಭಾಗದಲ್ಲಿ ಮುದ್ರಿಸಲಾಗಿದೆ</string>
    <string name="verified">ಪರಿಶೀಲಿಸಲಾಗಿದೆ</string>
    <string name="error_invalid_id">ಅಮಾನ್ಯ ಆಟಿಕೆ ID. ದಯವಿಟ್ಟು ಪರಿಶೀಲಿಸಿ ಮತ್ತು ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ.</string>
    
    <!-- Artisan Profile -->
    <string name="artisan_profile">ಕಾರಿಗರ ಪ್ರೊಫೈಲ್</string>
    <string name="story_of_wood">ಮರದ ಕಥೆ</string>
    <string name="watch_making_process">ತಯಾರಿಕೆ ಪ್ರಕ್ರಿಯೆಯನ್ನು ವೀಕ್ಷಿಸಿ</string>
    <string name="download_certificate">ದೃಢೀಕರಣ ಪ್ರಮಾಣಪತ್ರವನ್ನು ಡೌನ್‌ಲೋಡ್ ಮಾಡಿ</string>
    <string name="created_on">ರಚಿಸಲಾಗಿದೆ: %s</string>
    
    <!-- Map Screen -->
    <string name="get_directions">ನಿರ್ದೇಶನಗಳನ್ನು ಪಡೆಯಿರಿ</string>
    <string name="workshop_timings">ಕಾರ್ಯಾಗಾರ ಸಮಯ</string>
    
    <!-- Catalog Screen -->
    <string name="all_toys">ಎಲ್ಲಾ</string>
    <string name="rocking_horses">ರಾಕಿಂಗ್ ಕುದುರೆಗಳು</string>
    <string name="puzzles">ಒಗಟುಗಳು</string>
    <string name="animal_figurines">ಪ್ರಾಣಿ ಪ್ರತಿಮೆಗಳು</string>
    <string name="traditional_dolls">ಸಾಂಪ್ರದಾಯಿಕ ಗೊಂಬೆಗಳು</string>
    
    <!-- Education Screen -->
    <string name="heritage_title">200 ವರ್ಷಗಳ ಪರಂಪರೆ</string>
    <string name="making_process">ತಯಾರಿಕೆ ಪ್ರಕ್ರಿಯೆ</string>
    <string name="gi_tag_protected">GI ಟ್ಯಾಗ್ ರಕ್ಷಿತ</string>
    <string name="step_wood_selection">ಮರ ಆಯ್ಕೆ</string>
    <string name="step_lathe_turning">ಲೇತ್ ತಿರುಗಿಸುವಿಕೆ</string>
    <string name="step_lac_application">ಲ್ಯಾಕ್ ಅಪ್ಲಿಕೇಶನ್</string>
    <string name="step_finishing">ಪರಿಷ್ಕರಣೆ ಮತ್ತು ಹೊಳಪು ಮಾಡುವಿಕೆ</string>
</resources>
10.2 Runtime Language Switching
Kotlin

object LocaleHelper {
    
    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        
        return context.createConfigurationContext(config)
    }
    
    fun getCurrentLocale(context: Context): String {
        return context.resources.configuration.locales[0].language
    }
}
11. TESTING & QUALITY ASSURANCE
11.1 Unit Testing
Kotlin

@Test
fun `verify toy with valid ID returns success state`() = runTest {
    // Given
    val validToyId = "123456"
    val mockToy = ToyInfo(toyId = validToyId, artisanId = "ART001")
    val mockArtisan = Artisan(artisanId = "ART001", name = "Rangappa")
    
    coEvery { toyRepository.getToyById(validToyId) } returns mockToy
    coEvery { artisanRepository.getArtisanById("ART001") } returns mockArtisan
    coEvery { geminiRepository.generateArtisanStory(any(), any(), any()) } returns "Story"
    
    // When
    viewModel.verifyToy(validToyId)
    
    // Then
    val state = viewModel.uiState.value
    assertTrue(state is VerificationUiState.Success)
}

@Test
fun `verify toy with invalid ID returns error state`() = runTest {
    // Given
    val invalidToyId = "999999"
    coEvery { toyRepository.getToyById(invalidToyId) } returns null
    
    // When
    viewModel.verifyToy(invalidToyId)
    
    // Then
    val state = viewModel.uiState.value
    assertTrue(state is VerificationUiState.Error)
}
11.2 UI Testing (Compose)
Kotlin

@Test
fun homeScreen_displays_all_navigation_cards() {
    composeTestRule.setContent {
        HomeScreen(navController = rememberNavController())
    }
    
    composeTestRule.onNodeWithText("Verify My Toy").assertIsDisplayed()
    composeTestRule.onNodeWithText("Meet the Makers").assertIsDisplayed()
    composeTestRule.onNodeWithText("Toy Catalog").assertIsDisplayed()
    composeTestRule.onNodeWithText("How It's Made").assertIsDisplayed()
}

@Test
fun verificationScreen_accepts_6_digit_input() {
    composeTestRule.setContent {
        ToyIdInputScreen(
            onVerifyClick = {},
            onBackClick = {}
        )
    }
    
    // Enter 6 digits
    repeat(6) { index ->
        composeTestRule.onAllNodesWithText("")[index].performTextInput((index + 1).toString())
    }
    
    // Verify button should be enabled
    composeTestRule.onNodeWithText("Verify Now").assertIsEnabled()
}
11.3 Firebase Rules Testing
JavaScript

// firestore.rules.test.js
const firebase = require('@firebase/rules-unit-testing');

describe('Firestore Security Rules', () => {
  it('should allow public read access to toys collection', async () => {
    const db = firebase.initializeTestApp({ projectId: 'test' }).firestore();
    const toysRef = db.collection('toys').doc('123456');
    
    await firebase.assertSucceeds(toysRef.get());
  });
  
  it('should deny public write access to toys collection', async () => {
    const db = firebase.initializeTestApp({ projectId: 'test' }).firestore();
    const toysRef = db.collection('toys').doc('123456');
    
    await firebase.assertFails(toysRef.set({ name: 'Test Toy' }));
  });
});
12. DEPLOYMENT CHECKLIST
12.1 Pre-Deployment Steps
Firebase Configuration
 Create Firebase project
 Add Android app to Firebase console
 Download and add google-services.json
 Enable Firestore Database
 Enable Firebase Storage
 Configure Security Rules
 Upload sample data (5 workshops, 9 toys, 3 artisans)
 Test Firebase connection
Google Services
 Enable Google Maps API
 Add Maps API key to local.properties
 Enable Gemini API access
 Add Gemini API key to BuildConfig
Code Quality
 Run Lint checks: ./gradlew lint
 Fix all critical warnings
 Run unit tests: ./gradlew test
 Run UI tests: ./gradlew connectedAndroidTest
 Code review completed
String Localization
 All strings externalized to strings.xml
 Kannada translations complete
 Test language toggle functionality
12.2 Build Configuration
build.gradle.kts (app module)
Kotlin

android {
    defaultConfig {
        applicationId = "com.channapatna.nammapride"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        
        buildConfigField("String", "GEMINI_API_KEY", "\"${project.findProperty("GEMINI_API_KEY")}\"")
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
ProGuard Rules
proguard

# Keep Firebase models
-keep class com.channapatna.nammapride.data.model.** { *; }

# Keep Gemini AI classes
-keep class com.google.ai.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }
12.3 Signed APK Generation
Step 1: Generate Keystore
Bash

keytool -genkey -v -keystore channapatna-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias channapatna-key
Step 2: Configure Signing in build.gradle
Kotlin

android {
    signingConfigs {
        create("release") {
            storeFile = file("channapatna-release-key.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = "channapatna-key"
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
Step 3: Build Release APK
Bash

./gradlew assembleRelease
Output: app/build/outputs/apk/release/app-release.apk

12.4 Testing Checklist
Functional Testing
 Home screen loads in <3 seconds
 All 4 navigation buttons work
 Toy verification with valid ID shows artisan profile
 Toy verification with invalid ID shows error message
 AI story generates in <5 seconds
 Map displays 5+ workshop pins
 Tapping map pin shows workshop details
 "Get Directions" opens Google Maps
 Catalog displays 9+ toys in grid
 Category filtering works correctly
 Education screen displays all process steps
 Language toggle switches all UI text
 Kannada text renders correctly (no garbled characters)
Performance Testing
 App cold start <3 seconds
 Firebase query response <2 seconds on 4G
 No memory leaks (use Android Profiler)
 Smooth scrolling in LazyColumn/LazyVerticalGrid
 Image loading is async with placeholders
Device Testing
 Tested on Android 8.0 (API 26)
 Tested on Android 14 (API 34)
 Tested on small screen (5")
 Tested on large screen (6.5"+)
 Tested with different font sizes
 Tested in portrait and landscape modes
12.5 Deployment Platforms
Option 1: Firebase App Distribution (Internal Testing)
Bash

# Install Firebase CLI
npm install -g firebase-tools

# Login to Firebase
firebase login

# Deploy APK
firebase appdistribution:distribute app/build/outputs/apk/release/app-release.apk \
  --app YOUR_FIREBASE_APP_ID \
  --groups "testers" \
  --release-notes "Initial MVP release with all Must-Have features"
Option 2: Google Play Console (Alpha/Beta Testing)
Create Google Play Developer account
Create new app in Play Console
Upload APK to Internal Testing track
Add testers via email addresses
Publish to testing track
13. FINAL PROJECT STRUCTURE
text

ChannapatnaPride/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/channapatna/nammapride/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── ChannapatnaApplication.kt
│   │   │   │   ├── di/
│   │   │   │   │   ├── AppModule.kt
│   │   │   │   │   └── NetworkModule.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Type.kt
│   │   │   │   │   │   └── Theme.kt
│   │   │   │   │   ├── navigation/
│   │   │   │   │   │   └── NavGraph.kt
│   │   │   │   │   ├── home/
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   └── HomeViewModel.kt
│   │   │   │   │   ├── verification/
│   │   │   │   │   │   ├── VerificationScreen.kt
│   │   │   │   │   │   ├── ArtisanProfileScreen.kt
│   │   │   │   │   │   └── VerificationViewModel.kt
│   │   │   │   │   ├── map/
│   │   │   │   │   │   ├── MapScreen.kt
│   │   │   │   │   │   └── MapViewModel.kt
│   │   │   │   │   ├── catalog/
│   │   │   │   │   │   ├── CatalogScreen.kt
│   │   │   │   │   │   └── CatalogViewModel.kt
│   │   │   │   │   └── education/
│   │   │   │   │       ├── EducationScreen.kt
│   │   │   │   │       └── EducationViewModel.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/
│   │   │   │   │   │   ├── Artisan.kt
│   │   │   │   │   │   ├── ToyInfo.kt
│   │   │   │   │   │   ├── Workshop.kt
│   │   │   │   │   │   ├── Toy.kt
│   │   │   │   │   │   └── EducationalContent.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── ToyRepository.kt
│   │   │   │   │   │   ├── ArtisanRepository.kt
│   │   │   │   │   │   ├── WorkshopRepository.kt
│   │   │   │   │   │   ├── EducationRepository.kt
│   │   │   │   │   │   └── GeminiAIRepository.kt
│   │   │   │   │   └── util/
│   │   │   │   │       ├── LocaleHelper.kt
│   │   │   │   │       └── Constants.kt
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   │   └── logo_toy.xml
│   │   │   │   ├── values/
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   └── themes.xml
│   │   │   │   ├── values-kn/
│   │   │   │   │   └── strings.xml
│   │   │   │   └── AndroidManifest.xml
│   │   │   └── google-services.json
│   │   └── test/
│   │       └── (Unit tests)
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── local.properties (NOT in Git)
└── README.md