package com.jieehd.villain.toolkit;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabDisplay extends TabActivity {
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Kernels
        TabSpec kernelspec = tabHost.newTabSpec("Kernel");
        // setting Title and Icon for the Tab
        kernelspec.setIndicator("Kernel");
        Intent tab1Intent = new Intent(this, KernelTab.class);
        kernelspec.setContent(tab1Intent);
 
        // Tab for ROMs
        TabSpec romspec = tabHost.newTabSpec("ROM");
        romspec.setIndicator("ROM");
        Intent tab2Intent = new Intent(this, ROMTab.class);
        romspec.setContent(tab2Intent);
 
        // Tab for Tweaks
        TabSpec tweaksspec = tabHost.newTabSpec("Tweaks");
        tweaksspec.setIndicator("Tweaks");
        Intent tab3Intent = new Intent(this, TweaksTab.class);
        tweaksspec.setContent(tab3Intent);
        
        // Tab for info
        TabSpec aboutspec = tabHost.newTabSpec("About");
        aboutspec.setIndicator("About");
        Intent tab4Intent = new Intent(this, AboutTab.class);
        aboutspec.setContent(tab4Intent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(kernelspec); // Adding kernels tab
        tabHost.addTab(romspec); // Adding roms tab
        tabHost.addTab(tweaksspec); // Adding tweaks tab
        tabHost.addTab(aboutspec); // Adding about tab
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);  
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
            	Intent settingsIntent = new Intent(this, Settings.class);
            	startActivity(settingsIntent);
                return true;
            case R.id.downloads:
            	Intent downloadsIntent = new Intent(this, Downloads.class);
            	startActivity(downloadsIntent);
            	return true;
            case R.id.accounts:
            	Intent accountsIntent = new Intent(this, AccountsScreen.class);
            	startActivity(accountsIntent);
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}