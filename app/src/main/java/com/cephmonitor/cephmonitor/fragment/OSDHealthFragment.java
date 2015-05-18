package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OnOsdBoxClickListener;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OnStatusChangeListener;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdBox;
import com.cephmonitor.cephmonitor.layout.component.osdhealthboxes.OsdHealthBoxes;
import com.cephmonitor.cephmonitor.layout.fragment.OSDHealthLayout;

import java.util.ArrayList;

public class OSDHealthFragment extends Fragment {
    private OSDHealthLayout layout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new OSDHealthLayout(getActivity());
        }
        return layout;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<OsdBox> boxes = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            OsdBox box = new OsdBox();
            box.setStatus((int) (Math.random() * 2));
            box.value = i;
            boxes.add(box);
        }

        layout.boxesContainer.setData(boxes);

        layout.boxesContainer.setOnOsdBoxClickListener(clickOsdBox);
        layout.boxesContainer.setOnStatusChangeListener(changeShowedBox);

        layout.leftButton.setOnClickListener(clickLeftButton);
        layout.rightButton.setOnClickListener(clickRightButton);
        layout.centerButton.setOnClickListener(clickCenterButton);

        layout.leftButton.changeClickColor();
    }

    private OnOsdBoxClickListener clickOsdBox = new OnOsdBoxClickListener() {
        @Override
        public void onClick(OsdHealthBoxes boxGroup, OsdBox box) {
            Toast.makeText(getActivity(), box.value + "", Toast.LENGTH_SHORT).show();
        }
    };

    private OnStatusChangeListener changeShowedBox = new OnStatusChangeListener() {
        @Override
        public void onChange(OsdHealthBoxes boxGroup, ArrayList boxes) {
            if (boxes.size() > 0) {
                layout.hideWorkFind();
            } else {
                layout.showWorkFind();
            }
        }
    };

    private View.OnClickListener clickLeftButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.ALL);
        }
    };

    private View.OnClickListener clickCenterButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.WARN);
        }
    };

    private View.OnClickListener clickRightButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layout.recoverButtons();
            layout.boxesContainer.setShowStatus(OsdHealthBoxes.ERROR);
        }
    };
}
