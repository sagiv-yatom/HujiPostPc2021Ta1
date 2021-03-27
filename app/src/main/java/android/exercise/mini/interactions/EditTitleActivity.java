package android.exercise.mini.interactions;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {

  private boolean isEditing = false;

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_title);

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setAlpha(1f);
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setAlpha(0f);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("Page title here");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {
      this.isEditing = true;

      // animate out the "start edit" FAB
      fabStartEdit.animate()
              .alpha(0f)
              .setDuration(300L)
              .start();
      fabStartEdit.setVisibility(View.GONE);

      // animate in the "done edit" FAB
      fabEditDone.setVisibility(View.VISIBLE);
      fabEditDone.animate()
              .alpha(1f)
              .setDuration(300L)
              .start();

      // hide the static title (text-view)
      textViewTitle.setVisibility(View.GONE);

      // show the editable title (edit-text)
      editTextTitle.setVisibility(View.VISIBLE);

      // make the keyboard to open with the edit-text focused
      editTextTitle.requestFocus();
      editTextTitle.setSelection(editTextTitle.getText().length()); // place cursor at the end of editTextTitle
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.showSoftInput(editTextTitle, InputMethodManager.SHOW_IMPLICIT);
    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      this.isEditing = false;

      // animate out the "done edit" FAB
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(300L)
              .start();
      fabEditDone.setVisibility(View.GONE);

      // animate in the "start edit" FAB
      fabStartEdit.setVisibility(View.VISIBLE);
      fabStartEdit.animate()
              .alpha(1f)
              .setDuration(300L)
              .start();

      // take the text from the user's input in the edit-text and put it inside the static text-view
      String content = editTextTitle.getText().toString();
      textViewTitle.setText(content);

      // show the static title (text-view)
      textViewTitle.setVisibility(View.VISIBLE);

      // hide the editable title (edit-text)
      editTextTitle.setVisibility(View.GONE);

      // close keyboard
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(textViewTitle.getWindowToken(), 0);
    });
  }

  @Override
  public void onBackPressed() {
    // BACK button was clicked

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    // if user is now editing, tap on BACK will revert the edit
    if (this.isEditing) {
      this.isEditing = false;

      // hide the edit-text
      editTextTitle.setVisibility(View.GONE);

      // show the static text-view with previous text
      textViewTitle.setVisibility(View.VISIBLE);

      // discard user's input
      String content = textViewTitle.getText().toString();
      editTextTitle.setText(content);

      // animate out the "done-edit" FAB
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(300L)
              .start();
      fabEditDone.setVisibility(View.GONE);

      // animate in the "start edit" FAB
      fabStartEdit.setVisibility(View.VISIBLE);
      fabStartEdit.animate()
              .alpha(1f)
              .setDuration(300L)
              .start();
    }
    else {
      super.onBackPressed();
    }
  }
}