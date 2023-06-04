package it.uniba.dib.sms222334.Fragmets;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import it.uniba.dib.sms222334.Activity.MainActivity;
import it.uniba.dib.sms222334.Models.Document;
import it.uniba.dib.sms222334.Models.Private;
import it.uniba.dib.sms222334.Models.PublicAuthority;
import it.uniba.dib.sms222334.Models.SessionManager;
import it.uniba.dib.sms222334.Models.User;
import it.uniba.dib.sms222334.Models.Veterinarian;
import it.uniba.dib.sms222334.R;
import it.uniba.dib.sms222334.Utils.UserRole;
import kotlin.jvm.Throws;

public class ProfileFragment extends Fragment {
    final static String TAG="ProfileFragment";
    public enum Type{PRIVATE,PUBLIC_AUTHORITY,VETERINARIAN,ANIMAL}
    public enum TabPosition{ANIMAL,VISIT,EXPENSE,RELATION,HEALTH,FOOD}

    public static class Tab{
        public TabPosition tabPosition;
    }

    private Tab previousTab;
    
    private ProfileFragment.Type profileType;

    Button editButton;
    TabLayout tabLayout;

    private int inflatedLayout;

    UserRole role;

    public ProfileFragment(){

    }

    public static ProfileFragment newInstance(User profile) {
        ProfileFragment myFragment = new ProfileFragment();

        Bundle args = new Bundle();

        switch (profile.getRole()){
            case VETERINARIAN:
                args.putParcelable("profile", (Veterinarian)profile);
                break;
            case PRIVATE:
                args.putParcelable("profile", (Private)profile);
                break;
            case PUBLIC_AUTHORITY:
                args.putParcelable("profile", (PublicAuthority)profile);
                break;
        }

        myFragment.setArguments(args);

        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.role=((User)getArguments().getParcelable("profile")).getRole();

        switch (this.role){
            case PRIVATE:
                Log.d(TAG,"sono nel profilo di un privato");
                profileType=Type.PRIVATE;
                break;
            case PUBLIC_AUTHORITY:
                Log.d(TAG,"sono nel profilo di un ente");
                profileType=Type.PUBLIC_AUTHORITY;
                break;
            case VETERINARIAN:
                Log.d(TAG,"sono nel profilo di un veterinario");
                profileType=Type.VETERINARIAN;
                break;
        }

        if(this.role==UserRole.VETERINARIAN){
            inflatedLayout=R.layout.veterinarian_profile_fragment;
        }
        else{
            inflatedLayout=R.layout.owner_profile_fragment;
        }

        final View layout= inflater.inflate(inflatedLayout,container,false);

        this.previousTab=new Tab();

        editButton=layout.findViewById(R.id.edit_button);

        editButton.setOnClickListener(v -> launchEditDialog());



        tabLayout=layout.findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        changeTab(TabPosition.ANIMAL,true);
                        break;

                    case 1:
                        changeTab(TabPosition.VISIT,true);
                        break;

                    case 2:
                        changeTab(TabPosition.EXPENSE,true);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        changeTab(TabPosition.ANIMAL,false);

        return layout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void launchEditDialog() {

        final Dialog editDialog=new Dialog(getContext());
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        switch (this.role){
            case PRIVATE:
                editDialog.setContentView(R.layout.private_profile_edit);
                break;
            case VETERINARIAN:
                editDialog.setContentView(R.layout.veterinarian_profile_edit);
                break;
            case PUBLIC_AUTHORITY:
                editDialog.setContentView(R.layout.authority_profile_edit);
                break;
        }


        Spinner prefixSpinner= editDialog.findViewById(R.id.prefix_spinner);
        ArrayAdapter<CharSequence> prefixAdapter= ArrayAdapter.createFromResource(getContext(),R.array.phone_prefixes,
                android.R.layout.simple_list_item_1);
        prefixAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefixSpinner.setAdapter(prefixAdapter);


        Button backButton= editDialog.findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> editDialog.cancel());

        editDialog.show();
        editDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editDialog.getWindow().setGravity(Gravity.BOTTOM);
    }



    private void changeTab(ProfileFragment.TabPosition tabType,Boolean withAnimation){
        Fragment fragment=null;
        int enterAnimation=0,exitAnimation=0;

        switch (tabType){
            case ANIMAL:
                if(previousTab.tabPosition!= TabPosition.ANIMAL) {
                    previousTab.tabPosition= TabPosition.ANIMAL;
                    fragment= ListFragment.newInstance(previousTab,this.profileType);
                    enterAnimation=withAnimation?R.anim.slide_right_in:0;
                    exitAnimation=withAnimation?R.anim.slide_right_out:0;
                }
                else{
                    return;
                }
                break;
            case VISIT:
                if(previousTab.tabPosition!= TabPosition.VISIT) {
                    if (previousTab.tabPosition == TabPosition.ANIMAL) {
                        enterAnimation=withAnimation?R.anim.slide_left_in:0;
                        exitAnimation=withAnimation?R.anim.slide_left_out:0;
                    }
                    else {
                        enterAnimation=withAnimation?R.anim.slide_right_in:0;
                        exitAnimation=withAnimation?R.anim.slide_right_out:0;
                    }

                    previousTab.tabPosition= TabPosition.VISIT;
                    fragment= ListFragment.newInstance(previousTab,this.profileType);
                }
                else{
                    return;
                }
                break;
            case EXPENSE:
                if(previousTab.tabPosition!= TabPosition.EXPENSE){
                    previousTab.tabPosition= TabPosition.EXPENSE;
                    fragment= ListFragment.newInstance(previousTab,this.profileType);
                    enterAnimation=withAnimation?R.anim.slide_left_in:0;
                    exitAnimation=withAnimation?R.anim.slide_left_out:0;
                }
                else{
                    return;
                }
                break;
            default:
                changeTab(TabPosition.ANIMAL,true);
                return;
        }
        FragmentManager fragmentManager=getParentFragmentManager();

        Log.d(TAG,"poco prima di lanciare il fragment");

        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enterAnimation, exitAnimation);
        transaction.replace(R.id.recycle_container,fragment).commit();
    }
}
