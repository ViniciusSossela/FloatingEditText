# FloatingEditText
Custom view with support to add drawable icon on left and right side of EditText

## Example

To run the sample project, clone the repo, open and run with Android Studio IDE.

![alt text](https://image.ibb.co/hnof2G/Screen_Shot_2018_01_04_at_11_23_22.png)

## Gradle Dependency

```xml
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

```xml
compile 'com.github.ViniciusSossela:MaterialComponents:1.3.12'
```

## XML Layout Example
```xml
<com.vsossella.materialcomponents.FloatingEditText
        android:id="@+id/valor_floating_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        app:edit_text_animate_up="false"
        app:edit_text_decimal_mask="true"
        app:edit_text_font_size="34sp"
        app:edit_text_font_type="light"
        app:edit_text_max_length="12"
        app:edit_text_value="0"
        app:floating_label_font_size="12sp"
        app:floating_label_text="Brazilian Monetary Value"
        app:icon_left="@drawable/r" />
```

## Author

Vinicius Sossella, viniciussossella@gmail.com
