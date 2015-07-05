package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.cephmonitor.cephmonitor.fragment.FragmentLauncher;
import com.cephmonitor.cephmonitor.fragment.HealthDetailFragment;
import com.cephmonitor.cephmonitor.fragment.HealthFragment;
import com.cephmonitor.cephmonitor.fragment.HostHealthFragment;
import com.cephmonitor.cephmonitor.fragment.MonHealthFragment;
import com.cephmonitor.cephmonitor.fragment.NotificationFragment;
import com.cephmonitor.cephmonitor.fragment.OSDHealthDetailFragment;
import com.cephmonitor.cephmonitor.fragment.OSDHealthFragment;
import com.cephmonitor.cephmonitor.fragment.PgStatusFragment;
import com.cephmonitor.cephmonitor.fragment.PoolIopsFragment;
import com.cephmonitor.cephmonitor.fragment.PoolListFragment;
import com.cephmonitor.cephmonitor.fragment.UsageStatusFragment;
import com.cephmonitor.cephmonitor.layout.ColorTable;
import com.cephmonitor.cephmonitor.layout.activity.MainLayout;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;
import com.resourcelibrary.model.view.dialog.RoundSimpleSelectDialog;

import java.util.HashMap;


public class MainActivity extends Activity implements InitFragment.Style {
    public MainLayout layout;
    public LoginParams loginInfo;
    public Activity activity;
    private HashMap<Class, InitFragment.Task> changeStyleTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new MainLayout(this);
        setContentView(layout);
        loginInfo = new LoginParams(this);
        activity = this;

        changeStyleTask = new HashMap<>();
        changeStyleTask.put(HealthFragment.class, showHealthFragment);
        changeStyleTask.put(HealthDetailFragment.class, showHealthDetailFragment);
        changeStyleTask.put(MonHealthFragment.class, showMonHealthFragment);
        changeStyleTask.put(OSDHealthDetailFragment.class, showOSDHealthDetailFragment);
        changeStyleTask.put(OSDHealthFragment.class, showOsdHealthFragment);
        changeStyleTask.put(NotificationFragment.class, showNotificationFragment);
        changeStyleTask.put(HostHealthFragment.class, showHostHealthFragment);
        changeStyleTask.put(PgStatusFragment.class, showPgStatusFragment);
        changeStyleTask.put(UsageStatusFragment.class, showUsageStatusFragment);
        changeStyleTask.put(PoolIopsFragment.class, showPoolIopsFragment);
        changeStyleTask.put(PoolListFragment.class, showPoolListFragment);

        FragmentLauncher.goHealthFragment(activity);
        layout.health.setBackgroundResource(R.drawable.icon06);
        layout.health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.health.setBackgroundResource(R.drawable.icon06);
                FragmentLauncher.goHealthFragment(activity);
            }
        });

        layout.usage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.usage.setBackgroundResource(R.drawable.icon08);
            }
        });

        layout.performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.recoverAllButton();
                layout.performance.setBackgroundResource(R.drawable.icon10);
            }
        });

        layout.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoundSimpleSelectDialog dialog = new RoundSimpleSelectDialog(activity);
                dialog.setPosition(0, layout.bottomBar.getHeight());

                dialog.addItem(getResources().getString(R.string.main_activity_option_notification), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentLauncher.goNotificationFragment(activity, null);
                    }
                });
                dialog.addItem(getResources().getString(R.string.main_activity_option_node_list), null);
                dialog.addItem(getResources().getString(R.string.main_activity_option_pool_list), null);
                dialog.addItem(getResources().getString(R.string.main_activity_option_logs), null);
                dialog.addItem(getResources().getString(R.string.main_activity_option_alert), null);
                dialog.addItem(getResources().getString(R.string.main_activity_option_setting), null);
                dialog.addItem(getResources().getString(R.string.main_activity_option_logout), null);

                dialog.show();
            }
        });

        choiceFragment();
    }

    private void choiceFragment() {
        Bundle arg = getIntent().getExtras();
        if (arg == null) return;
        String initMode = arg.getString("init_mode");
        if (initMode.equals("Notification Page")) {
            FragmentLauncher.goNotificationFragment(activity, null);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            CheckExitDialog.create(activity).show();
        }
    }


    public void setTitle(String title) {
        layout.setTitle(title);
    }

    @Override
    public void changeFragmentStyle(Fragment fragment) {
        InitFragment.Task task = changeStyleTask.get(fragment.getClass());
        task.action(fragment.getArguments());
    }

    private InitFragment.Task showHealthFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_health));
            layout.topBar.setBackgroundColor(ColorTable._E63427);
            layout.bottomBar.setVisibility(View.VISIBLE);
            layout.hideBack();
        }
    };

    private InitFragment.Task showHealthDetailFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_health_detail));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showOsdHealthFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_osd_health));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.VISIBLE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showOSDHealthDetailFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            int osdId = arg.getInt("0");
            int titleColor = arg.getInt("1");

            setTitle(osdId + "");
            layout.topBar.setBackgroundColor(titleColor);
            layout.bottomBar.setVisibility(View.VISIBLE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showMonHealthFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_mon_health));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };


    private InitFragment.Task showNotificationFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_notification));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.VISIBLE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showHostHealthFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_host_health));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showPgStatusFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_pg_status));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };
    private InitFragment.Task showUsageStatusFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_usage_status));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showPoolIopsFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_pool_iops));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showPoolListFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            setTitle(getResources().getString(R.string.main_activity_fragment_pool_list));
            layout.topBar.setBackgroundColor(ColorTable._CD2626);
            layout.bottomBar.setVisibility(View.GONE);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };
}