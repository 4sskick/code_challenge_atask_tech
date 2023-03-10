# Calculator Camera
---

The app should allow the user to capture arithmetic expressions (i.e. 1+2) either directly from the built-in camera or from an image file picked by the user from the filesystem. Once the input is provided the app should detect the expression in the picture and compute its result. The result should be stored locally so the user can browse recent operations.

## UI Design
![alt](https://drive.google.com/uc?export=view&id=1SwrkH1VCTyWFpYSxOn5ZPkqi0C-uGAfX)

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
----
## ToDo:
- unit test not configured yet
- already pre-populate data
- clean unused code

