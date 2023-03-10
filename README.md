# Calculator Camera
---

## Requirements:
- input can be provided by using the built-in camera directly or by picking a picture from the filesystem
- a 3rd party solution can be used to find an expression in a picture
- only very simple 2 argument operations must be supported (+,-,*,/) (i.e. 2+2, 3-1, etc)
- if there are multiple expressions in the picture take the first one
- there should be a screen that allows the user to browse recent results

## Notes:
- the app consists of multi flavor to build
- behavior of the app controlled at compile and at runtime

## Conditions:
The app should be configurable by following:
- theme (compile time)
  - red
  - green
- ui functionality (compile time)
  - only allow to pick image from filesystem
  - only allow to use built-in-camera
- storage engine (runtime)
  - store the expression (equation) in an encrypted file
  - store the expression (equation) in an encrypted DB

## Result:
on build process would be produce 4 variants from delivered source code:
- app-red-filesystem.apk
- app-red-built-in-camera.apk
- app-green-filesystem.apk
- app-green-built-in-camera.apk

## Approach:
- Modern Android Architecture (MVVM)
- coroutine

