
# 2048-OOP
object oriented implementation of 2048 game in ~~java~~ kotlin.

check basic implementation in  [Sample.kt](https://github.com/mhashim6/2048-OOP/blob/master/src/mhashim6/game1024/Sample.kt).

output (from a loop, not AI):
```
UP
	4	4	64	256
	8	16	32	128
	0	32	16	32
	2	4	8	16
-------------------------------------------
RIGHT
	0	8	64	256
	8	16	32	128
	2	32	16	32
	2	4	8	16
-------------------------------------------
DOWN
	0	8	64	256
	2	16	32	128
	8	32	16	32
	4	4	8	16
-------------------------------------------
LEFT
	8	64	256	2
	2	16	32	128
	8	32	16	32
	32	0	0	0
-------------------------------------------
UNDO
	0	8	64	256
	2	16	32	128
	8	32	16	32
	4	4	8	16
-------------------------------------------
UP
	2	8	64	256
	8	16	32	128
	4	32	16	32
	2	4	8	16
-------------------------------------------
RIGHT
-mhashim6.game1024.exceptions.GameOverException: You Lose!
105 turns in time: 130 ms
```

## TODO (hopefully soon):
 - Allow for extension, to use in GUI's.
