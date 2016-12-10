package es.carlosrolindez.navigatorenh;


import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity 
{
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

            this.overridePendingTransition(R.anim.in_left,
                    R.anim.out_right);  
	        
	        // Display the fragment as the main content.
	        getFragmentManager().beginTransaction()
	                .add(android.R.id.content, new SettingsFragment())
	                .commit();
	    }
	  @Override
	  public void finish()
	  {
		  super.finish();
          this.overridePendingTransition(R.anim.in_right,
                  R.anim.out_left);  
	  }
}
