package com.channapatna.nammapride.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.view.MotionEvent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.channapatna.nammapride.data.Artisan
import com.channapatna.nammapride.data.EducationStep
import com.channapatna.nammapride.data.Toy
import com.channapatna.nammapride.data.VerifiedToy
import com.channapatna.nammapride.data.Workshop
import com.channapatna.nammapride.ui.theme.AppBackground
import com.channapatna.nammapride.ui.theme.Indigo
import com.channapatna.nammapride.ui.theme.Ink
import com.channapatna.nammapride.ui.theme.LacRed
import com.channapatna.nammapride.ui.theme.LeafGreen
import com.channapatna.nammapride.ui.theme.Turmeric
import kotlinx.coroutines.launch

private val Mango = Color(0xFFF2994A)
private val Rose = Color(0xFFFFE4DE)
private val Paper = Color(0xFFFFFBF3)
private val Clay = Color(0xFF8A4B2A)

private enum class Destination(val route: String, val label: String, val icon: ImageVector) {
    Home("home", "Home", Icons.Default.Badge),
    Verify("verify", "Verify", Icons.Default.CheckCircle),
    Catalog("catalog", "Catalog", Icons.Default.Inventory2),
    Makers("makers", "Makers", Icons.Default.Person),
    Learn("learn", "Learn", Icons.Default.Book)
}

@Composable
fun ChannapatnaApp(viewModel: ChannapatnaViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Destination.Home.route
    val contentState by viewModel.contentState.collectAsState()
    val language by viewModel.language.collectAsState()
    val t = remember(language) { Strings(language) }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 10.dp) {
                Destination.entries.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = { navController.navigate(destination.route) { launchSingleTop = true } },
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(t.navLabel(destination), maxLines = 1) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = LacRed,
                            indicatorColor = LacRed,
                            unselectedIconColor = Ink.copy(alpha = 0.58f),
                            unselectedTextColor = Ink.copy(alpha = 0.58f)
                        )
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable(Destination.Home.route) {
                HomeScreen(
                    t = t,
                    onToggleLanguage = viewModel::toggleLanguage,
                    onVerify = { navController.navigate(Destination.Verify.route) },
                    onCatalog = { navController.navigate(Destination.Catalog.route) },
                    onMakers = { navController.navigate(Destination.Makers.route) },
                    onLearn = { navController.navigate(Destination.Learn.route) }
                )
            }
            composable(Destination.Verify.route) { VerifyScreen(viewModel, t) }
            composable(Destination.Catalog.route) { CatalogScreen(contentState.toys, t) }
            composable(Destination.Makers.route) { MakersScreen(contentState.artisans, contentState.workshops, t) }
            composable(Destination.Learn.route) { LearnScreen(contentState.educationSteps, t) }
        }
    }
}

@Composable
private fun HomeScreen(
    t: Strings,
    onToggleLanguage: () -> Unit,
    onVerify: () -> Unit,
    onCatalog: () -> Unit,
    onMakers: () -> Unit,
    onLearn: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { LanguageToggle(t, onToggle = onToggleLanguage) }
        item { HeroPanel(t) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                ActionTile(t.verifyMyToy, t.verifySubtitle, Icons.Default.Search, LacRed, Modifier.weight(1f), onVerify)
                ActionTile(t.toyCatalog, t.catalogSubtitle, Icons.Default.Inventory2, Indigo, Modifier.weight(1f), onCatalog)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                ActionTile(t.meetMakers, t.makersHomeSubtitle, Icons.Default.Person, LeafGreen, Modifier.weight(1f), onMakers)
                ActionTile(t.howMade, t.learnSubtitle, Icons.Default.Book, Mango, Modifier.weight(1f), onLearn)
            }
        }
        item { MetricStrip(t) }
    }
}

@Composable
private fun HeroPanel(t: Strings) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFFFFF2B8), Rose, Color(0xFFE8F7F1))
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.85f), RoundedCornerShape(8.dp))
    ) {
        HeritagePattern(Modifier.matchParentSize())
        GeneratedToyArt(
            colors = listOf(LacRed, Turmeric, LeafGreen, Indigo),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 10.dp)
                .size(124.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(t.heroEyebrow, style = MaterialTheme.typography.labelLarge, color = Clay, fontWeight = FontWeight.Bold)
            Text(t.channapatna, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Black, color = Ink)
            Text(t.nammaPride, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = LacRed)
            Text(
                t.heroCopy,
                style = MaterialTheme.typography.bodyLarge,
                color = Ink.copy(alpha = 0.78f)
            )
        }
    }
}

@Composable
private fun ActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .height(142.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(CircleShape).background(color), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = title, tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                Box(Modifier.size(10.dp).clip(CircleShape).background(color.copy(alpha = 0.28f)))
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, maxLines = 2)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.64f), maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
private fun LanguageToggle(t: Strings, onToggle: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Button(onClick = onToggle, shape = RoundedCornerShape(8.dp)) {
            Text(t.languageButton)
        }
    }
}

@Composable
private fun MetricStrip(t: Strings) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        MetricCard("6", t.digitId, LacRed, Modifier.weight(1f))
        MetricCard("5+", t.workshops, LeafGreen, Modifier.weight(1f))
        MetricCard("200", t.years, Indigo, Modifier.weight(1f))
    }
}

@Composable
private fun MetricCard(value: String, label: String, color: Color, modifier: Modifier) {
    ElevatedCard(
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = color)
            Text(label, style = MaterialTheme.typography.labelLarge, color = Ink.copy(alpha = 0.65f))
        }
    }
}

@Composable
private fun VerifyScreen(viewModel: ChannapatnaViewModel, t: Strings) {
    val state by viewModel.verificationState.collectAsState()

    LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { ScreenTitle(t.verifyMyToy, t.verifyScreenSubtitle) }
        item {
            ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    OutlinedTextField(
                        value = state.toyId,
                        onValueChange = viewModel::updateToyId,
                        label = { Text(t.toyId) },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = t.toyId) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = viewModel::verifyToy,
                        enabled = state.toyId.length == 6 && !state.isLoading,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Verify")
                        Spacer(Modifier.width(8.dp))
                        Text(if (state.isLoading) t.checking else t.verifyNow)
                    }
                    Text(t.sampleIds, style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.62f))
                }
            }
        }
        state.error?.let { item { StatusCard(it, LacRed) } }
        state.result?.let { item { VerifiedToyCard(it, t) } }
    }
}

@Composable
private fun VerifiedToyCard(result: VerifiedToy, t: Strings) {
    ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(42.dp).clip(CircleShape).background(LeafGreen), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = Color.White)
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(t.authenticToy, fontWeight = FontWeight.Black, color = LeafGreen)
                    Text(result.toy.giTagNumber, style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.62f))
                }
            }
            GeneratedToyArt(result.toy.colors.map { Color(it) }, Modifier.fillMaxWidth().height(150.dp))
            Text(result.toy.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
            Text("${result.toy.category} / ${t.created} ${result.toy.creationDate}", color = Ink.copy(alpha = 0.7f))
            MakerBadge(result.artisan, t)
            Text(if (t.isKannada) t.storyOfWoodKannada else t.storyOfWood, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
            Text(if (t.isKannada) result.storyKannada else result.story, style = MaterialTheme.typography.bodyMedium, color = Ink.copy(alpha = 0.78f))
            WorkshopMiniCard(result.workshop)
        }
    }
}

@Composable
private fun CatalogScreen(toys: List<Toy>, t: Strings) {
    var selectedCategory by remember { mutableStateOf(t.all) }
    val categories = listOf(t.all, t.rockingHorses, t.traditionalDolls, t.miniatureVehicles)
    val filtered = if (selectedCategory == t.all) toys else {
        // Map Kannada category names back to English for filtering
        val englishCategory = when (selectedCategory) {
            t.rockingHorses -> "Rocking Horses"
            t.traditionalDolls -> "Traditional Dolls"
            t.miniatureVehicles -> "Miniature Vehicles"
            else -> selectedCategory
        }
        toys.filter { it.category == englishCategory }
    }

    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        ScreenTitle(t.toyCatalog, t.catalogScreenSubtitle)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(158.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filtered) { toy -> ToyCard(toy, t) }
        }
    }
}

@Composable
private fun ToyCard(toy: Toy, t: Strings) {
    ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            GeneratedToyArt(toy.colors.map { Color(it) }, Modifier.fillMaxWidth().height(104.dp))
            Text(toy.name, fontWeight = FontWeight.Black, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(toy.category, style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.62f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${t.id} ${toy.toyId}", style = MaterialTheme.typography.labelLarge, color = LacRed, fontWeight = FontWeight.Bold)
                Spacer(Modifier.weight(1f))
                Text("Rs ${toy.price}", fontWeight = FontWeight.Black)
            }
        }
    }
}

private fun artisanBio(artisan: Artisan, t: Strings): String =
    when {
        t.isKannada && artisan.bioKannada.isNotBlank() -> artisan.bioKannada
        else -> artisan.bio
    }

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun WorkshopsMap(
    workshops: List<Workshop>,
    onWorkshopSelected: (String) -> Unit,
    onMapInteractionChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val channapatna = LatLng(12.6518, 77.2069)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(channapatna, 13f)
    }
    val scope = rememberCoroutineScope()

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color(0xFFEDE0C8), RoundedCornerShape(8.dp))
                .pointerInteropFilter { event ->
                    when (event.actionMasked) {
                        MotionEvent.ACTION_DOWN,
                        MotionEvent.ACTION_MOVE -> onMapInteractionChanged(true)
                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_CANCEL -> onMapInteractionChanged(false)
                    }
                    false // Never consume events, let map handle everything
                }
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = false),
                uiSettings = MapUiSettings(zoomControlsEnabled = true, compassEnabled = true)
            ) {
                workshops.forEach { workshop ->
                    val position = LatLng(workshop.latitude, workshop.longitude)
                    Marker(
                        state = MarkerState(position = position),
                        title = workshop.name,
                        snippet = workshop.address,
                        onClick = {
                            onWorkshopSelected(workshop.workshopId)
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(position, 14f)
                                )
                            }
                            true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MakersScreen(artisans: List<Artisan>, workshops: List<Workshop>, t: Strings) {
    var selectedWorkshopId by remember { mutableStateOf<String?>(null) }
    var mapInteracting by remember { mutableStateOf(false) }
    val selectedWorkshop = workshops.firstOrNull { it.workshopId == selectedWorkshopId }

    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        userScrollEnabled = !mapInteracting
    ) {
        item { ScreenTitle(t.meetMakers, t.makersSubtitle) }
        item {
            WorkshopsMap(
                workshops = workshops,
                onWorkshopSelected = { selectedWorkshopId = it },
                onMapInteractionChanged = { mapInteracting = it }
            )
        }
        selectedWorkshop?.let { workshop ->
            item { WorkshopPinCard(workshop = workshop, t = t) }
        }
        items(artisans) { artisan ->
            val workshop = workshops.first { it.workshopId == artisan.workshopId }
            ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Box(
                        Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(LacRed)
                    )
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 14.dp, top = 14.dp, end = 14.dp, bottom = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MakerBadge(artisan, t)
                        Text(
                            artisanBio(artisan, t),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Ink.copy(alpha = 0.76f)
                        )
                        WorkshopMiniCard(workshop)
                    }
                }
            }
        }
        item { ScreenTitle(t.workshopsTitle, t.workshopsSubtitle) }
        items(workshops) { workshop ->
            WorkshopCard(
                workshop = workshop,
                t = t,
                isSelected = workshop.workshopId == selectedWorkshopId,
                onSelect = { selectedWorkshopId = workshop.workshopId }
            )
        }
    }
}

@Composable
private fun WorkshopPinCard(workshop: Workshop, t: Strings) {
    val uriHandler = LocalUriHandler.current
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Paper),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(workshop.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
            Text(workshop.timings, style = MaterialTheme.typography.bodyMedium, color = Ink.copy(alpha = 0.75f))
            Button(
                onClick = {
                    uriHandler.openUri("geo:${workshop.latitude},${workshop.longitude}?q=${workshop.latitude},${workshop.longitude}(${workshop.name})")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Store, contentDescription = "Directions")
                Spacer(Modifier.width(8.dp))
                Text(t.getDirections)
            }
        }
    }
}

@Composable
private fun MakerBadge(artisan: Artisan, t: Strings) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(LacRed, Mango))),
            contentAlignment = Alignment.Center
        ) {
            Text(artisan.name.take(1), color = Color.White, fontWeight = FontWeight.Black, style = MaterialTheme.typography.headlineSmall)
        }
        Spacer(Modifier.width(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(artisan.name, fontWeight = FontWeight.Black)
            Text(artisan.nameKannada, color = LacRed, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("${artisan.experience} ${t.yearsLower} / ${artisan.specialization}", style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.66f))
        }
    }
}

@Composable
private fun WorkshopCard(
    workshop: Workshop,
    t: Strings,
    isSelected: Boolean = false,
    onSelect: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) Modifier.border(2.dp, LacRed, RoundedCornerShape(8.dp))
                else Modifier
            )
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isSelected) Paper else Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (isSelected) 5.dp else 3.dp
        )
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEAF1FF)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Store, contentDescription = "Workshop", tint = Indigo)
                }
                Spacer(Modifier.width(10.dp))
                Text(workshop.name, fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium)
            }
            Text(workshop.address, style = MaterialTheme.typography.bodyMedium, color = Ink.copy(alpha = 0.72f))
            Text("${workshop.timings} / ${workshop.phone}", style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.66f))
            Text("${t.mapPin}: ${workshop.latitude}, ${workshop.longitude}", style = MaterialTheme.typography.bodySmall, color = LeafGreen, fontWeight = FontWeight.Bold)
            Button(
                onClick = {
                    onSelect()
                    uriHandler.openUri("geo:${workshop.latitude},${workshop.longitude}?q=${workshop.latitude},${workshop.longitude}(${workshop.name})")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Store, contentDescription = "Directions")
                Spacer(Modifier.width(8.dp))
                Text(t.getDirections)
            }
        }
    }
}

@Composable
private fun WorkshopMiniCard(workshop: Workshop) {
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Paper)
            .border(1.dp, Color(0xFFEDE0C8), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(workshop.name, fontWeight = FontWeight.Bold)
            Text(workshop.address, style = MaterialTheme.typography.bodySmall, color = Ink.copy(alpha = 0.68f))
        }
    }
}

@Composable
private fun LearnScreen(steps: List<EducationStep>, t: Strings) {
    LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { ScreenTitle(t.learnTitle, t.learnScreenSubtitle) }
        item {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFFE8F7F1), Color(0xFFFFF1D7))))
                    .padding(16.dp)
            ) {
                HeritagePattern(Modifier.matchParentSize())
                Text(
                    t.learnHero,
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
            }
        }
        items(steps) { StepCard(it, t) }
        item { StepCard(EducationStep(t.giProtection, t.giProtectionBody, 0xFFC62828), t) }
        item { StepCard(EducationStep(t.naturalLacquer, t.naturalLacquerBody, 0xFFFBC02D), t) }
        item { StepCard(EducationStep(t.artisanIdentity, t.artisanIdentityBody, 0xFF1565C0), t) }
    }
}

@Composable
private fun StepCard(step: EducationStep, t: Strings) {
    val title = if (t.isKannada && step.titleKannada.isNotBlank()) step.titleKannada else step.title
    val description = if (t.isKannada && step.descriptionKannada.isNotBlank()) step.descriptionKannada else step.description
    ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.width(58.dp), contentAlignment = Alignment.Center) {
                Canvas(Modifier.size(58.dp)) {
                    drawCircle(Color(step.accentColor).copy(alpha = 0.16f), radius = size.minDimension / 2f)
                    drawCircle(Color(step.accentColor), radius = size.minDimension / 4f)
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(title, fontWeight = FontWeight.Black, style = MaterialTheme.typography.titleMedium)
                Text(description, color = Ink.copy(alpha = 0.72f))
            }
        }
    }
}

@Composable
private fun ScreenTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Ink)
        Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = Ink.copy(alpha = 0.72f))
    }
}

@Composable
private fun StatusCard(message: String, color: Color) {
    ElevatedCard(shape = RoundedCornerShape(8.dp), colors = CardDefaults.elevatedCardColors(Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(10.dp).clip(CircleShape).background(color))
            Spacer(Modifier.width(10.dp))
            Text(message, color = color, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun GeneratedToyArt(colors: List<Color>, modifier: Modifier = Modifier) {
    val body = colors.getOrElse(0) { LacRed }
    val roof = colors.getOrElse(1) { Turmeric }
    val wheel = colors.getOrElse(2) { Indigo }
    val accent = colors.getOrElse(3) { LeafGreen }

    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Brush.linearGradient(listOf(Color.White, Paper)))
    ) {
        val w = size.width
        val h = size.height
        drawRoundRect(Color(0xFFFFF4D6), topLeft = Offset(w * 0.06f, h * 0.12f), size = Size(w * 0.88f, h * 0.76f), cornerRadius = CornerRadius(18.dp.toPx()))
        drawLine(Clay.copy(alpha = 0.26f), Offset(w * 0.1f, h * 0.76f), Offset(w * 0.9f, h * 0.76f), strokeWidth = 4.dp.toPx(), cap = StrokeCap.Round)
        drawRoundRect(body, topLeft = Offset(w * 0.2f, h * 0.42f), size = Size(w * 0.56f, h * 0.2f), cornerRadius = CornerRadius(14.dp.toPx()))
        drawRoundRect(roof, topLeft = Offset(w * 0.34f, h * 0.26f), size = Size(w * 0.28f, h * 0.18f), cornerRadius = CornerRadius(10.dp.toPx()))
        drawRoundRect(accent, topLeft = Offset(w * 0.66f, h * 0.34f), size = Size(w * 0.16f, h * 0.16f), cornerRadius = CornerRadius(10.dp.toPx()))
        drawCircle(wheel, radius = h * 0.105f, center = Offset(w * 0.33f, h * 0.68f))
        drawCircle(wheel, radius = h * 0.105f, center = Offset(w * 0.65f, h * 0.68f))
        drawCircle(Color.White, radius = h * 0.045f, center = Offset(w * 0.33f, h * 0.68f))
        drawCircle(Color.White, radius = h * 0.045f, center = Offset(w * 0.65f, h * 0.68f))
        drawCircle(Clay.copy(alpha = 0.12f), radius = h * 0.22f, center = Offset(w * 0.83f, h * 0.22f), style = Stroke(width = 3.dp.toPx()))
    }
}

@Composable
private fun HeritagePattern(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        repeat(5) { index ->
            val x = size.width * (0.12f + index * 0.2f)
            drawCircle(
                color = Color.White.copy(alpha = 0.22f),
                radius = size.minDimension * (0.12f + index * 0.015f),
                center = Offset(x, size.height * 0.24f),
                style = Stroke(width = 2.dp.toPx())
            )
        }
        repeat(7) { index ->
            drawLine(
                color = Clay.copy(alpha = 0.08f),
                start = Offset(size.width * index / 6f, 0f),
                end = Offset(size.width * (index / 6f + 0.18f), size.height),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

private class Strings(private val language: AppLanguage) {
    val isKannada = language == AppLanguage.Kannada
    private val kn = isKannada

    val languageButton = if (kn) "English" else "ಕನ್ನಡ"
    val channapatna = if (kn) "ಚನ್ನಪಟ್ಟಣ" else "Channapatna"
    val nammaPride = if (kn) "ನಮ್ಮ ಹೆಮ್ಮೆ" else "Namma Pride"
    val heroEyebrow = if (kn) "GI ಗುರುತಿನ ಮರದ ಆಟಿಕೆಗಳು" else "GI-tagged wooden toys"
    val heroCopy = if (kn) "ಪ್ರತಿ ನಿಜವಾದ ಆಟಿಕೆಗೆ ಡಿಜಿಟಲ್ ಗುರುತು, ಅದನ್ನು ತಯಾರಿಸಿದ ಕಲಾವಿದ, ಕಾರ್ಯಾಗಾರ ಮತ್ತು ಲ್ಯಾಕರ್ ಕಥೆಯೊಂದಿಗೆ ಜೋಡಿಸುತ್ತದೆ." else "A digital identity card for every authentic toy, linking buyers to the artisan, workshop, and story behind the lacquer."

    val verifyMyToy = if (kn) "ನನ್ನ ಆಟಿಕೆ ಪರಿಶೀಲಿಸಿ" else "Verify My Toy"
    val verifySubtitle = if (kn) "6 ಅಂಕಿಯ GI ಗುರುತು ಪರಿಶೀಲಿಸಿ" else "Check a 6-digit GI identity"
    val verifyScreenSubtitle = if (kn) "ಆಟಿಕೆಯ ಕೆಳಗೆ ಮುದ್ರಿಸಿರುವ 6 ಅಂಕಿಯ ಕೋಡ್ ನಮೂದಿಸಿ." else "Enter the 6-digit code printed under the Toy."
    val toyCatalog = if (kn) "ಆಟಿಕೆ ಪಟ್ಟಿ" else "Toy Catalog"
    val catalogSubtitle = if (kn) "ದೃಢೀಕೃತ ಗುರುತುಗಳು ಮತ್ತು ಬಣ್ಣ ವಿನ್ಯಾಸಗಳನ್ನು ನೋಡಿ." else "Browse verified identities and color palettes."
    val catalogScreenSubtitle = if (kn) "ದೃಢೀಕೃತ ಗುರುತುಗಳು ಮತ್ತು ಬಣ್ಣ ವಿನ್ಯಾಸಗಳನ್ನು ನೋಡಿ." else "Browse verified identities and color palettes."
    val rockingHorses = if (kn) "ಲಾಕೆ ಕುದುಗಳು" else "Rocking Horses"
    val puzzles = if (kn) "ಸಮಸ್ಯ" else "Puzzles"
    val traditionalDolls = if (kn) "ಸಂಪ್ರದಾಯಿ ಡಾಲ್ಗಳು" else "Traditional Dolls"
    val howMade = if (kn) "ಹೇಗೆ ತಯಾರಿಸಲಾಗುತ್ತದೆ" else "How It's Made"
    val learnSubtitle = if (kn) "ಮರ, ಲ್ಯಾಕರ್, ಪೂರ್ಣಗೊಳಿಸುವಿಕೆ" else "Wood, lacquer, finishing"
    val learnTitle = if (kn) "200 ವರ್ಷಗಳ ಪರಂಪರೆ" else "200 Years of Heritage"
    val learnScreenSubtitle = if (kn) "ಮರದಿಂದ ಪೂರ್ಣಗೊಂಡ ಆಟಿಕೆವರೆಗೆ ಸಂರಕ್ಷಿತ ಚನ್ನಪಟ್ಟಣ ಪ್ರಕ್ರಿಯೆ." else "The protected Channapatna process from wood to finished toy."
    val learnHero = if (kn) "ಪ್ರತಿ ವಕ್ರವನ್ನು ಲೇತ್ ಮೇಲೆ ರೂಪಿಸಿ, ಲ್ಯಾಕರ್ ಬಣ್ಣ ನೀಡಿ, ಮಿನುಗುವ ಚನ್ನಪಟ್ಟಣ ಸ್ಪರ್ಶಕ್ಕೆ ಪೂರ್ಣಗೊಳಿಸಲಾಗುತ್ತದೆ." else "Every curve is shaped on a lathe, colored with lacquer, and finished for the unmistakable glossy Channapatna feel."

    val digitId = if (kn) "ಅಂಕಿಯ ID" else "digit ID"
    val workshops = if (kn) "ಕಾರ್ಯಾಗಾರಗಳು" else "workshops"
    val miniatureVehicles = if (kn) "ಚಿನ್ನೆ ವಾಹನಗಳು" else "Miniature Vehicles"
    val educationalPuzzles = if (kn) "ಶೈಕ್ಷಣಿಕ ಆಟಿಕೆಗಳು" else "Educational Puzzles"
    val years = if (kn) "ವರ್ಷಗಳು" else "years"
    val yearsLower = years
    val toyId = if (kn) "ಆಟಿಕೆ ID" else "Toy ID"
    val checking = if (kn) "ಪರಿಶೀಲಿಸಲಾಗುತ್ತಿದೆ..." else "Checking..."
    val verifyNow = if (kn) "ಈಗ ಪರಿಶೀಲಿಸಿ" else "Verify Now"
    val sampleIds = if (kn) "ಮಾದರಿ ID 123456, 234567 ಅಥವಾ 345678 ಪ್ರಯತ್ನಿಸಿ." else "Try sample ID 123456, 234567, or 345678."
    val authenticToy = if (kn) "ನಿಜವಾದ GI ಗುರುತಿನ ಆಟಿಕೆ" else "Authentic GI-tagged toy"
    val created = if (kn) "ರಚಿಸಿದ ದಿನ" else "Created"
    val storyOfWood = if (kn) "ಮರದ ಕಥೆ" else "Story of the wood"
    val all = if (kn) "ಎಲ್ಲಾ" else "All"
    val id = if (kn) "ID" else "ID"

    val meetMakers = if (kn) "ಕಲಾವಿದರನ್ನು ಭೇಟಿ ಮಾಡಿ" else "Meet the Makers"
    val makersHomeSubtitle = if (kn) "ಕಲಾವಿದರು ಮತ್ತು ಕಾರ್ಯಾಗಾರಗಳು" else "Artisans and workshops"
    val makersSubtitle = if (kn) "ಕಾರ್ಯಾಗಾರ ಸ್ಥಳಗಳಿಗೆ ಸಂಪರ್ಕಿತ ಪ್ರೊಫೈಲ್‌ಗಳು." else "Profiles connected to workshop locations."
    val workshopsTitle = if (kn) "ಕಾರ್ಯಾಗಾರಗಳು" else "Workshops"
    val workshopsSubtitle = if (kn) "ಚನ್ನಪಟ್ಟಣದ ಸಕ್ರಿಯ ಆಟಿಕೆ ತಯಾರಿಕಾ ಸ್ಥಳಗಳಿಗೆ ಭೇಟಿ ನೀಡಿ." else "Visit active toy-making locations around Channapatna."
    val mapPin = if (kn) "ನಕ್ಷೆ ಪಿನ್" else "Map pin"
    val getDirections = if (kn) "ದಿಕ್ಕುಗಳನ್ನು ಪಡೆಯಿರಿ" else "Get directions"
    val adminTitle = if (kn) "ನಿರ್ವಹಣೆ" else "Admin"
    val adminHomeSubtitle = if (kn) "ಕಲಾವಿದರನ್ನು ಸೇರಿಸಿ" else "Add artisans"
    val adminSubtitle = if (kn) "ನಿರ್ವಹಕರಾಗಿ ಲಾಗಿನ್ ಮಾಡಿ ಹೊಸ ಕಲಾವಿದರನ್ನು ಸೇರಿಸಿ." else "Sign in as admin and add new artisans."
    val adminLoginTitle = if (kn) "ನಿರ್ವಹಕ ಲಾಗಿನ್" else "Admin Login"
    val adminEmail = if (kn) "ಇಮೇಲ್" else "Email"
    val adminPassword = if (kn) "ಪಾಸ್ವರ್ಡ್" else "Password"
    val adminSignIn = if (kn) "ಲಾಗಿನ್" else "Sign In"
    val adminSignOut = if (kn) "ಲಾಗ್ಔಟ್" else "Sign Out"
    val addArtisanTitle = if (kn) "ಹೊಸ ಕಲಾವಿದರನ್ನು ಸೇರಿಸಿ" else "Add New Artisan"
    val adminArtisanId = if (kn) "ಕಲಾವಿದ ID" else "Artisan ID"
    val adminArtisanName = if (kn) "ಹೆಸರು (ಇಂಗ್ಲಿಷ್)" else "Name (English)"
    val adminArtisanNameKannada = if (kn) "ಹೆಸರು (ಕನ್ನಡ)" else "Name (Kannada)"
    val adminVillage = if (kn) "ಗ್ರಾಮ" else "Village"
    val adminExperienceYears = if (kn) "ಅನುಭವ (ವರ್ಷಗಳು)" else "Experience (years)"
    val adminSpecialization = if (kn) "ತಜ್ಞತೆ" else "Specialization"
    val adminWorkshopId = if (kn) "ಕಾರ್ಯಾಗಾರ ID" else "Workshop ID"
    val adminAvailableWorkshops = if (kn) "ಲಭ್ಯ ಕಾರ್ಯಾಗಾರಗಳು" else "Available workshops"
    val adminBioEnglish = if (kn) "ಪರಿಚಯ (ಇಂಗ್ಲಿಷ್)" else "Bio (English)"
    val adminBioKannada = if (kn) "ಪರಿಚಯ (ಕನ್ನಡ)" else "Bio (Kannada)"
    val adminAddArtisan = if (kn) "ಕಲಾವಿದರನ್ನು ಸೇರಿಸಿ" else "Add Artisan"

    val addWorkshopTitle = if (kn) "ಹೊಸ ಕಾರ್ಯಾಗಾರವನ್ನು ಸೇರಿಸಿ" else "Add New Workshop"
    val adminWorkshopName = if (kn) "ಕಾರ್ಯಾಗಾರದ ಹೆಸರು" else "Workshop Name"
    val adminWorkshopAddress = if (kn) "ವಿಳಾಸ" else "Address"
    val adminWorkshopPhone = if (kn) "ದೂರವಾಣಿ" else "Phone"
    val adminWorkshopTimings = if (kn) "ಸಮಯ" else "Timings"
    val adminWorkshopLatitude = if (kn) "ಅಕ್ಷಾಂಶ" else "Latitude"
    val adminWorkshopLongitude = if (kn) "ರೇಖಾಂಶ" else "Longitude"
    val adminAddWorkshop = if (kn) "ಕಾರ್ಯಾಗಾರವನ್ನು ಸೇರಿಸಿ" else "Add Workshop"

    val giProtection = if (kn) "GI ರಕ್ಷಣೆ" else "GI Protection"
    val giProtectionBody = if (kn) "ಚನ್ನಪಟ್ಟಣ ಆಟಿಕೆಗಳು ಸಂರಕ್ಷಿತ ಭೌಗೋಳಿಕ ಗುರುತನ್ನು ಹೊತ್ತಿವೆ, ಇದು ಖರೀದಿದಾರರಿಗೆ ನಿಜವಾದ ಸ್ಥಳೀಯ ಕಲೆ ಗುರುತಿಸಲು ಸಹಾಯ ಮಾಡುತ್ತದೆ." else "Channapatna toys carry a protected geographical identity, helping buyers recognize authentic local craft and discouraging counterfeits."
    val naturalLacquer = if (kn) "ನೈಸರ್ಗಿಕ ಲ್ಯಾಕರ್" else "Natural Lacquer"
    val naturalLacquerBody = if (kn) "ಮರ ಲೇತ್ ಮೇಲೆ ತಿರುಗುತ್ತಿರುವಾಗ ಲ್ಯಾಕರ್ ಹಚ್ಚಲಾಗುತ್ತದೆ, ಇದರಿಂದ ಮೃದುವಾದ ಕೈತಯಾರಿಕೆಯ ಮಿನುಗು ಬರುತ್ತದೆ." else "The shine comes from lacquer applied while the wood turns warm on the lathe, creating a finish that feels smooth and distinctly handmade."
    val artisanIdentity = if (kn) "ಕಲಾವಿದರ ಗುರುತು" else "Artisan Identity"
    val artisanIdentityBody = if (kn) "ಪ್ರತಿ ದೃಢೀಕೃತ ಆಟಿಕೆಯನ್ನು ಅದನ್ನು ತಯಾರಿಸಿದ ಕಲಾವಿದರೊಂದಿಗೆ ಜೋಡಿಸಬಹುದು, ಖರೀದಿಯನ್ನು ವೈಯಕ್ತಿಕವಾಗಿಸುತ್ತದೆ." else "Each verified toy can connect back to a craftsperson, making the purchase feel personal instead of anonymous."
    val storyOfWoodKannada = if (kn) "ಮರ ಆಯ್ಕೆ ಮಾಡಲಾಗುತ್ತದೆ ಹೆಸರುವಾಸಿದ ಕಲಾವಿದೆ." else "Story of the Wood"
    val storyKannada = if (kn) "ಮರ ಆಯ್ಕೆ ಮಾಡಲಾಗುತ್ತದೆ ಹೆಸರುವಾಸಿದ ಕಲಾವಿದೆ." else "Story of the Wood"

    fun navLabel(destination: Destination): String {
        return when (destination) {
            Destination.Home -> if (kn) "ಮುಖಪುಟ" else "Home"
            Destination.Verify -> if (kn) "ಪರಿಶೀಲನೆ" else "Verify"
            Destination.Catalog -> if (kn) "ಪಟ್ಟಿ" else "Catalog"
            Destination.Makers -> if (kn) "ಕಲಾವಿದರು" else "Makers"
            Destination.Learn -> if (kn) "ಕಲಿ" else "Learn"
        }
    }
}
