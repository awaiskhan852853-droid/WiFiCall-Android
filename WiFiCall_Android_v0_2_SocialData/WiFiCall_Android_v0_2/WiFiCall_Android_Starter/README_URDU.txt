WiFi Call - Android Starter Project
===================================

یہ پہلا مرحلہ (Version 0.1.0) ہے: Android ایپ کا قابلِ تعمیر UI prototype۔

اس میں:
- Home screen
- WiFi Call ID input
- Contacts demo list
- Call screen
- Call History اور Settings tabs

اہم وضاحت:
یہ ورژن ابھی حقیقی آڈیو کال نہیں کرتا۔ کال بٹن صرف بتاتا ہے کہ WebRTC calling server اگلا مرحلہ ہے۔
یہ جان بوجھ کر واضح رکھا گیا ہے تاکہ اسے تیار کاروباری calling service سمجھنے کی غلطی نہ ہو۔

Windows پر چلانے کا طریقہ:
1. ZIP فائل Extract کریں۔
2. Android Studio کھولیں۔
3. Open پر کلک کریں۔
4. WiFiCall_Android_Starter فولڈر منتخب کریں۔
5. Gradle Sync مکمل ہونے دیں (پہلی بار انٹرنیٹ درکار ہوگا)۔
6. موبائل میں Developer Options اور USB debugging آن کریں، USB سے کمپیوٹر سے جوڑیں۔
7. Android Studio میں Run ▶ دبائیں۔

اگر Android Studio ابھی انسٹال نہیں:
- Android Studio سرکاری ویب سائٹ سے انسٹال کریں۔
- Android SDK Platform 35 انسٹال کریں۔
- JDK 17 منتخب کریں۔

اگلا تکنیکی مرحلہ:
1. User accounts + secure sign-in
2. WebRTC audio engine
3. Signaling server (offer/answer/ICE exchange)
4. STUN/TURN configuration تاکہ مختلف WiFi/mobile networks پر کال connect ہو سکے
5. Incoming-call notifications
6. Call history and abuse prevention

WebRTC نوٹ:
WebRTC خود signaling service فراہم نہیں کرتا۔ دونوں فونز کے درمیان connection details کا تبادلہ کرنے کے لیے signaling server درکار ہے۔ بعض نیٹ ورکس پر TURN relay بھی ضروری ہوتا ہے۔

کاروباری مرحلہ:
عام Jazz/Zong/دیگر فون نمبروں پر کال کے لیے کسی مجاز telecom/SIP provider کے ساتھ معاہدہ، rate limits، billing، user verification، privacy اور پاکستان کے قابلِ اطلاق telecom قواعد کی جانچ ضروری ہوگی۔
اس starter میں ابھی PSTN/عام نمبر dialing یا wallet/payment شامل نہیں۔

پروجیکٹ معلومات:
App name: WiFi Call
Application ID: com.wificall.app
Version: 0.1.0
Minimum Android: 6.0 (API 23)
Target SDK: 35
Language: Kotlin
UI: Jetpack Compose

سرکاری رہنمائی:
Android: https://developer.android.com/get-started/overview
WebRTC: https://webrtc.org/getting-started/peer-connections
