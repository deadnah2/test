# ISD.ICT.20242 Group 12

# Team member
| Name             | Student ID  | Role        |
| :--------------- | :---------- | :---------- |
| Nguyen Manh Dung | 20194745    | Member      |
| Vu Duc Manh 	   | 20226054    | Member      |
| Vo Phu Loc	   | 20215221    | Member      |
| Mai Tuan Kien	   | 20226051    | Member      |
| Dang Nam Anh	   | 20187209    | Member      |

## Technology
Java + JavaFx

## How to install
1. Install JavaFx 22.0.1 .
2. Create a new `User Library` under `Eclipse` -> `Window` -> `Preferences` -> `Java` -> `Build Path` -> `User Libraries` -> `New`
3. Name it anything you want, and include all **_jars_** under the extracted folder (Ex: "C:\Users\Admin\javafx-sdk-22.0.1\lib") that you downloaded in step 1.
4. Include the library into the classpath of the project (Right click on the project -> `Build path` -> `Configure Build Path` -> `Libraries` -> `Classpath`).

### Add VM arguments
Click on `Run` -> `Run Configurations...` -> `Java Application`, create a new launch configuration for your project and
add these VM arguments:

  > `--module-path <Your javafx lib folder address> --add-modules javafx.controls,javafx.graphics,javafx.fxml,javafx.media,javafx.web`

