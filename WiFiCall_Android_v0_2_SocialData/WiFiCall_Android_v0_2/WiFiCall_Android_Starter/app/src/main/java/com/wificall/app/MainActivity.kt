package com.wificall.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF10243A)
private val Teal = Color(0xFF08A88A)
private val Pale = Color(0xFFF3F7FA)

data class Contact(val name: String, val status: String, val initials: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Teal,
                    secondary = Navy,
                    background = Pale,
                    surface = Color.White
                )
            ) {
                WiFiCallApp { message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
fun WiFiCallApp(showMessage: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    var dialNumber by remember { mutableStateOf("") }
    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    val contacts = remember {
        listOf(
            Contact("Demo Friend", "WiFi Call user (demo)", "DF"),
            Contact("Family Member", "Invite them to WiFi Call", "FM"),
            Contact("Work Contact", "Invite them to WiFi Call", "WC")
        )
    }

    if (selectedContact != null) {
        CallScreen(
            contact = selectedContact!!,
            onBack = { selectedContact = null },
            onCall = {
                showMessage("Calling is not connected yet. The next development step is the WebRTC calling server.")
            }
        )
        return
    }

    Scaffold(
        containerColor = Pale,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Call, null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.History, null) },
                    label = { Text("History") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Settings") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(46.dp).background(Teal, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Phone, null, tint = Color.White)
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("WiFi Call", fontSize = 23.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Text("Talk through the internet", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(Modifier.height(22.dp))

            when (selectedTab) {
                0 -> {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Navy),
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(20.dp)) {
                            Text("YOUR INTERNET CALLING", color = Color(0xFFB7D8E4), fontSize = 11.sp)
                            Spacer(Modifier.height(8.dp))
                            Text("Connect with people\nusing WiFi or data.", color = Color.White,
                                fontSize = 22.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(12.dp))
                            Text("App-to-App calling: planned for the next build",
                                color = Color(0xFFB7D8E4), fontSize = 12.sp)
                        }
                    }
                    Spacer(Modifier.height(22.dp))
                    Text("Dial a WiFi Call ID", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = dialNumber,
                        onValueChange = { dialNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter username or phone") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = {
                            if (dialNumber.isBlank()) showMessage("Enter a WiFi Call ID first.")
                            else showMessage("Account lookup and live calling will be connected in the next development stage.")
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Call, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Start Internet Call")
                    }
                    Spacer(Modifier.height(22.dp))
                    Text("Contacts", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Spacer(Modifier.height(8.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(contacts) { contact ->
                            ContactRow(contact) { selectedContact = contact }
                        }
                    }
                }
                1 -> {
                    Text("Call History", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Spacer(Modifier.height(12.dp))
                    Text("No calls yet.", color = Color.Gray)
                    Text("Your completed internet calls will appear here after calling is connected.",
                        color = Color.Gray, fontSize = 13.sp)
                }
                else -> {
                    Text("Settings", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Navy)
                    Spacer(Modifier.height(12.dp))
                    SettingRow("Account", "Demo mode")
                    SettingRow("Call quality", "Internet connection")
                    SettingRow("Wallet", "Not enabled in this prototype")
                    SettingRow("App version", "0.1.0")
                }
            }
        }
    }
}

@Composable
fun ContactRow(contact: Contact, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.padding(13.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(44.dp).background(Color(0xFFE0F4EF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(contact.initials, color = Teal, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(contact.name, fontWeight = FontWeight.SemiBold, color = Navy)
                Text(contact.status, fontSize = 11.sp, color = Color.Gray)
            }
            Icon(Icons.Default.Call, null, tint = Teal)
        }
    }
}

@Composable
fun CallScreen(contact: Contact, onBack: () -> Unit, onCall: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(Navy).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
            Text("← Back", color = Color.White)
        }
        Spacer(Modifier.height(48.dp))
        Box(Modifier.size(112.dp).background(Teal, CircleShape), contentAlignment = Alignment.Center) {
            Text(contact.initials, fontSize = 34.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(22.dp))
        Text(contact.name, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text("WiFi Call contact", color = Color(0xFFB7D8E4))
        Spacer(Modifier.height(48.dp))
        Button(onClick = onCall, shape = CircleShape, modifier = Modifier.size(76.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Teal)) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.White)
        }
        Spacer(Modifier.height(14.dp))
        Text("Calling will be enabled after server setup", color = Color(0xFFB7D8E4), fontSize = 12.sp)
    }
}

@Composable
fun SettingRow(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(title, color = Navy, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
            Text(value, color = Color.Gray, fontSize = 12.sp)
        }
    }
}
