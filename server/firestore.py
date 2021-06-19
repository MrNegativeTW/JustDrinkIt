import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

cred = credentials.Certificate(
    "iot-demo-f8b66-firebase-adminsdk-dxph6-9a5c20b91e.json")
firebase_admin.initialize_app(cred)

db = firestore.client()
doc_ref = db.collection(u'bottleStatus').document(u'7HQ26j7APOAghhMip5xH')

def setBottleExitstToTrue():
    doc_ref.set({u'isBottleExists': True})

def setBottleExitstToFalse():
    doc_ref.set({u'isBottleExists': False})