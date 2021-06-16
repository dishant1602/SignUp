# SignUp Preview
![ezgif com-gif-maker](https://user-images.githubusercontent.com/76876581/122236088-d9131c00-cedb-11eb-96d0-ee799778ca19.gif)

In collaboration with [Neel Shah](https://github.com/neelshah2409)

The following app will register user through mail id and password and will store additional information like name, Mail Id, Password, Contact in the Realtime Firebase Database.

You can remove `Firebase Database` section from the code if you don't wish to store user information and only wish to authenticate(register) using Mail ID and password.

```java
FirebaseUser fuser = auth.getCurrentUser();
String fuid = fuser.getUid();
HashMap<String, Object> hashMap = new HashMap<String, Object>();
hashMap.put("Name",str_name);
hashMap.put("Mail ID",str_mailid);
hashMap.put("Password",str_psswd);
hashMap.put("Contact",str_contact);
FirebaseDatabase.getInstance().getReference("Users").child(fuid).setValue(hashMap);
```

**Please remember to connect `Firebase` to your project using `Tools->Firebase`**

Here is a link to [Figma Sign Up UI](https://www.figma.com/community/file/967758010981428845) if you face any issues regarding layout in the code.

Happy coding :smile: :tada:
