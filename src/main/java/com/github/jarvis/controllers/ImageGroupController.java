package com.github.jarvis.controllers;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.jarvis.FormController;
import com.github.jarvis.ImageDataModel;
import com.github.jarvis.dynamicformbuilder.R;
import com.github.jarvis.validations.ImageClickListener;
import com.github.jarvis.validations.InputValidator;
import com.github.jarvis.validations.ValidationListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a field that allows a user to select multiple items from checkboxes.
 * <p/>
 * For the field value, the associated FormModel must return either a Set<String> or a Set<0-based index>, representing the
 * currently selected items. Which representation to use is specified by the constructor. In either representation, no
 * selection can be represented by returning {@code null} for the value of the field.
 */
public class ImageGroupController extends LabeledFieldController {
    private final int IMAGE_ID = FormController.generateViewId();
    private Context context;
    private List<ImageDataModel> items;
    private List<?> values;
    private ImageClickListener imageClickListener;
    private final int PICK_START_IMAGE_GALLERY = 101;
    RecyclerViewAdapter adapter;
    ArrayList<ImageDataModel> arrayList;
    private ValidationListener validationListener;

    /**
     * Constructs a new instance of a checkboxes field.
     *
     * @param ctx              the Android context
     * @param name             the name of the field
     * @param labelText        the label to display beside the field. Set to {@code null} to not show a label
     * @param validators       contains the validations to process on the field
     * @param items            a list of Strings defining the selection items to show
     * @param useItemsAsValues if true, {@code CheckBoxController} expects the associated form model to use
     *                         the same string of the selected item when getting or setting the field; otherwise,
     *                         {@code CheckBoxController} expects the form model to use index (as an Integer) to
     *                         represent the selected item
     */
    public ImageGroupController(Context ctx, String name, String labelText, Set<InputValidator> validators, List<ImageDataModel> items, boolean useItemsAsValues) {
        this(ctx, name, labelText, validators, items, useItemsAsValues ? items : null);
    }

    /**
     * Constructs a new instance of a checkboxes field.
     *
     * @param ctx        the Android context
     * @param name       the name of the field
     * @param labelText  the label to display beside the field
     * @param validators contains the validations to process on the field
     * @param items      a list of Strings defining the selection items to show
     * @param values     a list of Objects representing the values to set the form model on a selection (in
     *                   the same order as the {@code items}.
     */
    public ImageGroupController(Context ctx, String name, String labelText, Set<InputValidator> validators, List<ImageDataModel> items, List<?> values) {
        super(ctx, name, labelText, validators);
        this.context = ctx;
        this.items = items;
        this.values = values;

        if(values != null && items.size() != values.size()) {
            throw new IllegalArgumentException("Size of Values and Items must be equal.");
        }
    }


    /**
     * Constructs a new instance of a checkboxes field.
     *
     * @param ctx              the Android context
     * @param name             the name of the field
     * @param labelText        the label to display beside the field. Set to {@code null} to not show a label
     * @param isRequired       indicates if the field is required or not
     * @param imageDataModels            a list of Strings defining the selection items to show
     * @param useItemsAsValues if true, {@code CheckBoxController} expects the associated form model to use
     *                         the same string of the selected item when getting or setting the field; otherwise,
     *                         {@code CheckBoxController} expects the form model to use index (as an Integer) to
     *                         represent the selected item
     */
    public ImageGroupController(Context ctx, String name, String labelText, boolean isRequired, List<ImageDataModel> imageDataModels,
                                boolean useItemsAsValues, ImageClickListener imageclicklistener, ValidationListener validationListener) {
        this(ctx, name, labelText, isRequired, imageDataModels, useItemsAsValues ? imageDataModels : null, imageclicklistener, validationListener);
    }

    /**
     * Constructs a new instance of a checkboxes field.
     *
     * @param ctx        the Android context
     * @param name       the name of the field
     * @param labelText  the label to display beside the field
     * @param isRequired indicates if the field is required or not
     * @param items      a list of Strings defining the selection items to show
     * @param values     a list of Objects representing the values to set the form model on a selection (in
     *                   the same order as the {@code items}.
     */
    public ImageGroupController(Context ctx, String name, String labelText, boolean isRequired, List<ImageDataModel> items, List<?> values,
                                ImageClickListener imageClickListener, ValidationListener validationListener) {
        super(ctx, name, labelText, isRequired);
        this.items = items;
        this.values = values;
        this.imageClickListener = imageClickListener;
        this.validationListener = validationListener;

        if(values != null && items.size() != values.size()) {
            throw new IllegalArgumentException("Size of Values and Items must be equal.");
        }
    }

    @Override
    protected View createFieldView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {

            ViewGroup imageViewContainer = (ViewGroup) inflater.inflate(R.layout.form_imge_container, null);
            RecyclerView recyclerView = imageViewContainer.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

            int spanCount = 2; // 2 columns
            int spacing = 50; // 50px
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));

            adapter = new RecyclerViewAdapter(getContext(), items, new RecyclerViewAdapter.ItemListener() {
                @Override
                public void onItemClick(int postion, ImageDataModel item) {
                    imageClickListener.onImageClicked(postion, item);
                }
            });

            if(items != null && items.size() > 0) {
                arrayList = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    arrayList.add(items.get(i));
                    getModel().setValue(getName(), arrayList);
                    validationListener.onValidateInput();
                }
            }
            recyclerView.setAdapter(adapter);
            return imageViewContainer;
        } else return null;
    }

     public void refresh(ImageView imageView, int index) {
        Set<Object> modelValues = retrieveModelValues();
        /*imageView.setChecked(
                modelValues.contains(
                        areValuesDefined() ? values.get(index) : index
                )
        );*/

    }

    @Override
    public void refresh() {
        adapter.notifyDataSetChanged();
    }


    /**
     * Returns the status of the values entry.
     *
     * @return true if values entry can be used. false otherwise.
     */
    private boolean areValuesDefined() {
        return values != null;
    }

    /**
     * Returns the values hold in the model.
     *
     * @return The values from the model.
     */
    private Set<Object> retrieveModelValues() {
        Set<Object> modelValues = (Set<Object>) getModel().getValue(getName());
        if (modelValues == null) {
            modelValues = new HashSet<>();
        }
        return modelValues;
    }

    /**
     * Returns the View containing the images.
     *
     * @return The View containing the images.
     */
    private ViewGroup getContainer() {
        return (ViewGroup) getView().findViewById(R.id.image_container);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
