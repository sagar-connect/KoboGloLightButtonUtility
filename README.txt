As contributed to : https://www.mobileread.com/forums/showthread.php?t=231681

I have made a small utility app for Kobo Glo. Named it Kobo Glo Utilility :P

The intention of making this app is to make use of (otherwise useless) light button on Kobo-Glo within Android and to get rid of ghosts/artifacts while reading.

Download Link:
https://www.dropbox.com/s/rovcrfcna9...ility.apk?dl=0

With this app you can:
1. Configure Light button as a manual SCREEN REFRESH button.
OR Configure Light button as BACK button.

2. Additionally configure volume change action for screen refresh.
This may come handy if you prefer keeping light button as BACK (or set as NOTHING, but why would you do that? ) and still make use of manual screen refresh.
(You may use button savior for emulating volume button pressed action).

3. Bonus: Auto disable blinking of power/status LED at startup.

Note:
1. Changing button behavior will come in effect only after a reboot.
2. The refresh screen service should be running to listen for light button/volume event. (automatically runs at startup, if enabled).
3. Changing a button behavior will modify the file mxckpd.kl at system/usr/keylaout.
You might want to make a backup. (Change will persist even if you uninstall the app). You may choose 'NOTHING' to set it do default behavior.