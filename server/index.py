import time
import RPi.GPIO as GPIO
from apscheduler.schedulers.background import BackgroundScheduler
import fcm
import firestore


# Docs:
# https://apscheduler.readthedocs.io/en/v3.5.1/userguide.html#starting-the-scheduler
scheduler = BackgroundScheduler()

# Use BCM GPIO references instead of physical pin numbers
GPIO.setmode(GPIO.BCM)

GPIO_TRIGGER = 14  # HCSR04
GPIO_ECHO = 15  # HCSR04
MONITOR_PIN = 18  # HCSR501

# Set pins as output and input
GPIO.setup(GPIO_TRIGGER, GPIO.OUT)  # Trigger
GPIO.setup(GPIO_ECHO, GPIO.IN)  # Echo
GPIO.setup(MONITOR_PIN, GPIO.IN)  # MONITOR

checkUserExistsTimes = 0
breakWhenNoBottle = False


def isBottleExists():
    # print("是否小於 5 公分：", end="")

    # Set trigger to False (Low)
    GPIO.output(GPIO_TRIGGER, False)

    # Allow module to settle
    time.sleep(0.5)

    # Send 10us pulse to trigger
    GPIO.output(GPIO_TRIGGER, True)
    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False)
    start = time.time()
    while GPIO.input(GPIO_ECHO) == 0:
        start = time.time()

    while GPIO.input(GPIO_ECHO) == 1:
        stop = time.time()

    # Calculate pulse length
    elapsed = stop - start

    # Distance pulse travelled in that time is time
    # multiplied by the speed of sound (cm/s)
    distance = elapsed * 34000

    # That was the distance there and back so halve the value
    distance = distance / 2

    if distance <= 5.0:
        isBottleStillThere = True
        # print("是，%.1f 公分" % distance)
        return True
    else:
        isBottleStillThere = False
        # print("否，%.1f 公分" % distance)
        return False


def isUserExists():
    global checkUserExistsTimes
    global breakWhenNoBottle
    while True:
        detectResut = GPIO.input(MONITOR_PIN)
        print("是否在座位上：", end="")
        print(detectResut)

        if detectResut == 1:
            notiDrinkTheFuckingWater()
            # checkUserExistsTimes += 1

        # print("檢查使用者是否在位置上次數：", end="")
        # print(checkUserExistsTimes)

        # if checkUserExistsTimes != 0 and checkUserExistsTimes % 3 == 0:
        #     notiDrinkTheFuckingWater()
        #     break

        if breakWhenNoBottle == True:
            break

        print("三秒後再次檢查使用者是否在位置上")
        time.sleep(3)


def notiDrinkTheFuckingWater():
    fcm.sendNotification()


def handleJob(isBottleExists):
    global breakWhenNoBottle
    global checkUserExistsTimes

    a = scheduler.get_jobs()
    print("目前的任務列表：", a)

    if isBottleExists:
        firestore.setBottleExitstToTrue() # Set isBottleExists to true
        breakWhenNoBottle = False
        if len(a) == 0:
            scheduler.add_job(isUserExists,
                              "interval",
                              seconds=10,
                              id='job_id_here')
    else:
        firestore.setBottleExitstToFalse()  # Set isBottleExists to false
        breakWhenNoBottle = True
        checkUserExistsTimes = 0
        scheduler.remove_all_jobs()


if __name__ == '__main__':
    try:
        scheduler.start()
        while True:
            if isBottleExists():
                # print("瓶子還在")
                handleJob(True)
            else:
                # print("瓶子不在了 :(")
                handleJob(False)
            time.sleep(1)
    except KeyboardInterrupt:
        print('See you')
    finally:
        GPIO.cleanup()

# 每10秒偵測水在或不在
# 小於3公分：水還在
# 大於3公分L：水被拿起來過了

# A: 從第一次小於3公分起，配合動作感測器偵測計時30分鐘
# 30分鐘時偵測在不在
#   -> 在 通知
#   -> 不在，重置動作感測器偵測計時30分鐘