# Keys
Why trust other keyboards when you can have your own ?
Use this library to design your own keyboard and have full control of what the user is typing.
Available keyboards are,<br />
<b>1) Numeric Keyboard<br />
2) Qwerty Keyboard</b>

# How to Install Plugin
Add the below in your root build.gradle(project) at the end of repositories:<br />
<b>allprojects { </b><br />
<b>repositories { </b><br />
<b>... </b><br />
<b>maven { url 'https://jitpack.io' } </b><br />
<b>} </b><br />
<b>} </b><br />
            
Add the dependency in build.gradle(module) : <br />
<b>dependencies { </b><br />
<b>implementation 'com.github.Sivaranjan89:Keys:1.3'</b><br />
<b>}</b><br />


# Sample Screenshot
## Numeric
![one](https://user-images.githubusercontent.com/54542325/64918834-4d79d200-d7c1-11e9-892a-f6c08c6a2e17.png)

## Qwerty
![two](https://user-images.githubusercontent.com/54542325/64918837-62eefc00-d7c1-11e9-8d34-016e613c45cf.png)


## Sample Code
<com.droid.keys.NumericKeyboard<br />
        android:id="@+id/keyboard"<br />
        android:layout_width="match_parent"<br />
        android:layout_height="300dp"<br />
        android:layout_alignParentBottom="true"<br />
        android:layout_centerHorizontal="true"<br />
        app:cellColor="#DFDEDE"<br />
        app:textColor="#000000"<br />
        app:pressedTextColor="#FFFFFF"<br />
        app:pressedCellColor="#000000"<br />
        app:textStyle="bold"<br />
        app:textSize="20dp"<br />
        app:dividerColor="#000000"/><br />
        
<com.droid.keys.QwertyKeyboard<br />
        android:id="@+id/keyboard"<br />
        android:layout_width="match_parent"<br />
        android:layout_height="300dp"<br />
        android:layout_alignParentBottom="true"<br />
        android:layout_centerHorizontal="true"<br />
        app:cellColor="#DFDEDE"<br />
        app:textColor="#000000"<br />
        app:pressedTextColor="#FFFFFF"<br />
        app:pressedCellColor="#000000"<br />
        app:textStyle="bold"<br />
        app:textSize="20dp"/><br />
        
        
## Fetch user click
keyboard.onKeyClicked(new QwertyKeyboard.KeysClickEvent() {<br />
            @Override<br />
            public void onKeyClicked(String clickedKey) {<br />
                Toast.makeText(MainActivity.this, clickedKey, Toast.LENGTH_SHORT).show();<br />
            }<br />
        });<br />
        

