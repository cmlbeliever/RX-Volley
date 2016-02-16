package com.cml.frame.rx_volley;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void readConcats(View v) {
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String string = "联系人：";
//        if (null != c) {
//
//            while (c.moveToNext()) {
////                string += c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                string += getAllPhoneNumbers(c.getString(c.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY)));
//            }
//            c.close();
//        }
        string += getAllPhoneNumbers("");
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    /**
     * Get all the phone numbers of a specific contact person
     */
    public String getAllPhoneNumbers(String lookUp_Key) {
        String allPhoneNo = "";

        // Phone info are stored in the ContactsContract.Data table
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] proj2 = {ContactsContract.CommonDataKinds.Phone.NUMBER};
        // using lookUp key to search the phone numbers
//        String selection = ContactsContract.Data.LOOKUP_KEY + "=?";

        Cursor cur = getContentResolver().query(phoneUri, proj2, null, null, null);
        while (cur.moveToNext()) {
            allPhoneNo += cur.getString(0) + " ";
        }

        return allPhoneNo;
    }

    public void requestPermission(View v) {
        int readConcatCode = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        boolean hasReadConcatPermission = readConcatCode == PackageManager.PERMISSION_GRANTED;

        Toast.makeText(this, "是否有权限？" + hasReadConcatPermission, Toast.LENGTH_LONG).show();
        if (!hasReadConcatPermission) {


//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS},
//                    1);
//             Should we show an explanation?
            //被拒绝后，提示不同信息
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "shouldShowRequestPermissionRationale！！！", Toast.LENGTH_LONG).show();
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("这个权限是必须的，否则无法正常使用！");
                builder.setPositiveButton("授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(PermissionActivity.this,
                                new String[]{Manifest.permission.READ_CONTACTS},
                                1);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();


            } else {

                Toast.makeText(this, "shouldShowRequestPermissionRationale==false！！！", Toast.LENGTH_LONG).show();
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "onRequestPermissionsResult" + grantResults[0], Toast.LENGTH_LONG).show();
    }
}
