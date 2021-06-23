<div align="center">
  
<img src="https://fakeimg.pl/128x128/282828/eae0d0/?text=Icon" width="128" height="128">

<h1>Just Drink It</h1>
<h4>
他會提醒你喝水，包含 Server 端和 Android 客戶端原始碼。
</h4>

![](https://img.shields.io/badge/Raspberry%20Pi-Any-C51A4A?style=flat-square)
![](https://img.shields.io/badge/Python-3.7+-4B8BBE?style=flat-square)
![](https://img.shields.io/badge/Android%20Studio-4.1.2+-32DE84?style=flat-square)
![](https://img.shields.io/badge/Android-6.0+-32DE84?style=flat-square)
![](https://img.shields.io/badge/Firebase-Firestore-FFA611?style=flat-square)
![](https://img.shields.io/badge/Firebase-Cloud%20Message-FFA611?style=flat-square)

<p align="center">
  <a href="#Preview">Preview</a> •
  <a href="#features">Features</a> •
  <a href="#requirements">Requirements</a> •
  <a href="#installation">Installation</a> •
  <a href="#license">License</a>
</p>
</div>

## Preview
<img src="https://raw.githubusercontent.com/MrNegativeTW/JustDrinkIt/main/android_client_screenshot.jpg" width="240">

## Features

- Drink reminder

## Requirements

### Server

- Raspberry Pi
- Fast as fuck internet connection
- HCSR04
- HCSR501

### Android Client

- Android Studio 4.1.2+
- Android 6.0+

### Others

- Firebase Firestore
- Firebase Cloud Message


## Architecture

![](https://fakeimg.pl/1280x720/)


## Installation 

### Firebase

1. Create a firebase project

2. [Get your Admin SDK credentials](https://console.firebase.google.com/u/1/project/_/settings/serviceaccounts/adminsdk), <br>looks like  `YOUR_PROJECT-firebase-adminsdk-RANDOM_STRING.json`

3. [Get your Cloud Mesaage API Key](https://console.firebase.google.com/project/_/settings/cloudmessaging)

4. In Firestore, Create a `collection` called `bottleStatus`, than create a `document` with random id.

5. Copy that random id.


### Android Client

1. Get your `google-services.json` from Firebase when adding app, put it in to `./DrinkInClient/app`

2. Modify `FirestoreManager.py` line 12, replace with your random document id.
```kotlin
return db.collection("bottleStatus").document("RANDOM_ID")
    
```

3. Click `RUN`.

3. Get your `device token` by pressing the float action button, which will looks like this:

```
fWo6-TFJSVCDAV8h8DPRaQ:APA91bELNaBgKduEAWACJN-tIB8CkpDSkbloGIluxaCeMFLDjmpiz26UZqU4L-cW5VeYGK1GiMFywdcaalav8zNWCHurKu10ZnPUlH_w9YYm1WwftXrDv7X58YJNUwtdk60n6ebQWX1r
```


### Server Side

1. Modify `firestore.py` line 6, replace with your Admin SDK credentials path.

```python
cred = credentials.Certificate(
    "YOUR_PROJECT-firebase-adminsdk-RANDOM_STRING.json")
```

2. Modify `fcm.py` line 5, replace with your `Cloud Mesaage API Key`.

```python
api_key = "API_KEY_HERE"
```

3. Modify `firestore.py` line 10, replace with your random document id.

```python
doc_ref = db.collection(u'bottleStatus').document(u'RANDOM_ID')
```

4. Modify `fcm.py` line 8, replace with your `android device token`.

```python
device_token = "DEVICE_TOKEN_HERE"
```

5. Install dependences than start the server.

```bash
pip3 install -r requirements.txt
python3 index.txt
```

## Typo

I know. DrinkItClient become DrinkInClient.

## License
```
No idea.
```