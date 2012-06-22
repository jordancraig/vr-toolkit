package com.jieehd.villain.toolkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class Accounts extends DialogPreference implements OnSharedPreferenceChangeListener {
	
	private static AutoCompleteTextView user;
	private static EditText pass;
	private static Context cx;
	
	public Accounts(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setDialogLayoutResource(R.layout.accounts);
	}
	
	@Override
	protected void onBindDialogView(View view) {
		super.onBindDialogView(view);
		
		user = (AutoCompleteTextView) view.findViewById(R.id.usernameView);
		pass = (EditText) view.findViewById(R.id.passwordView);
	}
	
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        
        if (positiveResult) {
        	
        	String username = user.getText().toString();
        	String password = pass.getText().toString();
        	
        	SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(cx);
        	SharedPreferences.Editor editor = sharedPrefs.edit();
        	editor.putString("key_username", username);
        	editor.putString("key_password", password);
        	
        } else {
        	
        	user.setText("");
        	pass.setText("");
        	
        }
    }

	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
