# Send to single device.
from pyfcm import FCMNotification

# Your api-key can be gotten from:  https://console.firebase.google.com/project/<project-name>/settings/cloudmessaging
api_key = "AAAAXQQdzyU:APA91bG7sUQyYEHWLIl_mUPna5MuCz1vCnh_GDl820k48yrTFytm7_OeohpPhRvxz9Qdk6_pi4gRkLl73l1Ss6h1Kjt_1tSn58tWZv1ln-ET4hBn9XG8mGQ25yUELtdDUzoueeGCYP0T"

# Qemu on Ryzen
# device_token = "eOw0V7WYRWGSEgiCsrqt9Q:APA91bFJ2lWbt4Sv9PmvHxm9C2FzL2WxRxC069xb59ZQDfxnp1m81q_zxe0c08WW0jGkpG1QsugqLhx4wSk7LD2hbMhckVA4WIBE-7FTSuZ1Xkx_dEPTZ_SWaFlgI77S_pKO5lwFB__z"

# HTC X
device_token = "fWo6-TFJSVCDAV8h8DPRaQ:APA91bELNaBgKduEAWACJN-tIB8CkpDSkbloGIluxaCeMFLDjmpiz26UZqU4L-cW5VeYGK1GiMFywdcaalav8zNWCHurKu10ZnPUlH_w9YYm1WwftXrDv7X58YJNUwtdk60n6ebQWX1r"


def sendNotification():
    push_service = FCMNotification(api_key=api_key)

    message_title = "記得喝水"
    message_body = "你已經30分鐘沒喝水了QQ"
    result = push_service.notify_single_device(registration_id=device_token,
                                            message_title=message_title,
                                            message_body=message_body)

    print(result)