package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ma3.ui.components.publicComponents.RouteHeader

@Composable
fun HelpScreen(
    onNavigateBack: () -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    var expandedFaq by remember { mutableStateOf<Int?>(null) }
    var expandedCategory by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            RouteHeader(
                title = "Help & Support",
                onBackClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFDF7),
                            Color(0xFFF1F1F1)
                        )
                    )
                )
                .verticalScroll(rememberScrollState()) // scrollable
        ) {
            // Quick Action Cards
            Text(
                text = "Quick Actions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Default.Report,
                    title = "Report Issue",
                    modifier = Modifier.weight(1f),
                    onClick = { /* TODO: Implement report */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // FAQ Section
            Text(
                text = "Frequently Asked Questions",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val faqs = listOf(
                FAQ(
                    question = "How do I find the cheapest route?",
                    answer = "Enter your starting location and destination in the search fields on the home screen, then tap 'Find Cheapest Route'. The app will show you multiple route options sorted by price."
                ),
                FAQ(
                    question = "What payment methods are accepted?",
                    answer = "Matatus typically accept cash payments. Some modern matatus also accept mobile money payments like M-Pesa. Always confirm with the conductor before boarding."
                ),
                FAQ(
                    question = "How do I report incorrect information?",
                    answer = "Tap the 'Report Issue' button above or use the feedback form at the bottom of this page. We review all reports to improve our data accuracy."
                )
            )

            faqs.forEachIndexed { index, faq ->
                FAQItem(
                    faq = faq,
                    isExpanded = expandedFaq == index,
                    onClick = { expandedFaq = if (expandedFaq == index) null else index }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Browse by Category
            Text(
                text = "Browse by Category",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val categories = listOf(
                FAQ(
                    question = "Getting Started",
                    answer = "Learn how to set up and begin using MatatuGo, including basic app navigation and route searching."
                ),
                FAQ(
                    question = "Routes & Fares",
                    answer = "Understand how route pricing works, where to find stages, and what affects fare changes."
                ),
                FAQ(
                    question = "Account & Settings",
                    answer = "Manage your profile information, preferences, and app notifications easily from settings."
                ),
                FAQ(
                    question = "Safety & Tips",
                    answer = "Follow these safety guidelines and travel tips to make your matatu rides safer and smoother."
                )
            )

            categories.forEachIndexed { index, category ->
                FAQItem(
                    faq = category,
                    isExpanded = expandedCategory == index,
                    onClick = { expandedCategory = if (expandedCategory == index) null else index }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contact Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Still need help?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactItem(
                        icon = Icons.Default.Email,
                        title = "Email Us",
                        subtitle = "support@matatugo.co.ke"
                    )

                    ContactItem(
                        icon = Icons.Default.Language,
                        title = "Visit Website",
                        subtitle = "www.matatugo.co.ke"
                    )
                }
            }

            // Footer
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Terms of Service",
                    fontSize = 14.sp,
                    color = colors.primary,
                    modifier = Modifier
                        .clickable { /* TODO: Show terms */ }
                        .padding(vertical = 8.dp)
                )

                Text(
                    text = "Privacy Policy",
                    fontSize = 14.sp,
                    color = colors.primary,
                    modifier = Modifier
                        .clickable { /* TODO: Show privacy */ }
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "MatatuGo v1.0.0",
                    fontSize = 12.sp,
                    color = colors.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun QuickActionCard(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = colors.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface
            )
        }
    }
}

@Composable
fun FAQItem(
    faq: FAQ,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = colors.onSurface.copy(alpha = 0.6f)
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = faq.answer,
                    fontSize = 14.sp,
                    color = colors.onSurface.copy(alpha = 0.8f),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = colors.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = colors.primary
            )
        }
    }
}

data class FAQ(
    val question: String,
    val answer: String
)
