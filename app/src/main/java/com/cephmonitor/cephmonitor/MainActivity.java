package com.cephmonitor.cephmonitor;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;

import com.cephmonitor.cephmonitor.fragment.AlertTriggersFragment;
import com.cephmonitor.cephmonitor.fragment.FragmentLauncher;
import com.cephmonitor.cephmonitor.fragment.HealthDetailFragment;
import com.cephmonitor.cephmonitor.fragment.HealthFragment;
import com.cephmonitor.cephmonitor.fragment.HostDetailAllCpusFragment;
import com.cephmonitor.cephmonitor.fragment.HostDetailFragment;
import com.cephmonitor.cephmonitor.fragment.HostDetailIopsFragment;
import com.cephmonitor.cephmonitor.fragment.HostDetailSummaryFragment;
import com.cephmonitor.cephmonitor.fragment.HostHealthFragment;
import com.cephmonitor.cephmonitor.fragment.MonHealthFragment;
import com.cephmonitor.cephmonitor.fragment.NotificationDetailFragment;
import com.cephmonitor.cephmonitor.fragment.NotificationFragment;
import com.cephmonitor.cephmonitor.fragment.OSDHealthDetailFragment;
import com.cephmonitor.cephmonitor.fragment.OSDHealthFragment;
import com.cephmonitor.cephmonitor.fragment.PgStatusFragment;
import com.cephmonitor.cephmonitor.fragment.PoolIopsFragment;
import com.cephmonitor.cephmonitor.fragment.PoolListFragment;
import com.cephmonitor.cephmonitor.fragment.SettingsFragment;
import com.cephmonitor.cephmonitor.fragment.TimePeriodFragment;
import com.cephmonitor.cephmonitor.fragment.UsageStatusFragment;
import com.cephmonitor.cephmonitor.layout.activity.MainLayout;
import com.cephmonitor.cephmonitor.layout.component.tab.OnTabChangeListener;
import com.cephmonitor.cephmonitor.model.app.theme.custom.manager.ThemeManager;
import com.cephmonitor.cephmonitor.model.app.theme.custom.prototype.DesignSpec;
import com.cephmonitor.cephmonitor.model.ceph.constant.CephNotificationConstant;
import com.cephmonitor.cephmonitor.model.tool.RefreshViewManager;
import com.cephmonitor.cephmonitor.receiver.LoadFinishReceiver;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.view.dialog.CheckExitDialog;

import java.util.HashMap;


public class MainActivity extends Activity implements InitFragment.Style, RefreshViewManager.Interface {
    public MainLayout layout;
    public LoginParams loginInfo;
    public Activity activity;
    private HashMap<Class, InitFragment.Task> changeStyleTask;
    private DesignSpec designSpec;
    private int primary;
    private int secondary;
    private int broadcastResourceId;
    private int nextBroadcastResourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new MainLayout(this);
        setContentView(layout);
        loginInfo = new LoginParams(this);
        activity = this;
        designSpec = ThemeManager.getStyle(this);
        primary = designSpec.getPrimaryColors().getPrimary();
        secondary = designSpec.getPrimaryColors().getSecondary();

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
        changeStyleTask.put(HostDetailFragment.class, showHostDetailFragment);
        changeStyleTask.put(HostDetailSummaryFragment.class, showHostDetailSummaryFragment);
        changeStyleTask.put(HostDetailAllCpusFragment.class, showHostDetailAllCpusFragment);
        changeStyleTask.put(HostDetailIopsFragment.class, showHostDetailIopsFragment);
        changeStyleTask.put(NotificationDetailFragment.class, showNotificationDetailFragment);
        changeStyleTask.put(SettingsFragment.class, showSettingsFragment);
        changeStyleTask.put(AlertTriggersFragment.class, showAlertTriggersFragment);
        changeStyleTask.put(TimePeriodFragment.class, showTimePeriodFragment);

        layout.setNavigationTitleText(loginInfo.getName(), loginInfo.getHost());

        FragmentLauncher.goHealthFragment(activity);

        layout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (nextBroadcastResourceId == broadcastResourceId) return;

                broadcastResourceId = nextBroadcastResourceId;
                if (broadcastResourceId == R.string.main_activity_fragment_health) {
                    FragmentLauncher.goHealthFragment(activity);
                } else if (broadcastResourceId == R.string.main_activity_option_logout) {
                    ActivityLauncher.goLoginActivity(activity);
                    loginInfo.setIsLogin(false);
                    finish();
                } else if (broadcastResourceId == R.string.main_activity_fragment_notification) {
                    FragmentLauncher.goNotificationFragment(activity, null);
                } else if (broadcastResourceId == R.string.main_activity_option_logs) {
                    //TODO
                } else if (broadcastResourceId == R.string.main_activity_option_setting) {
                    FragmentLauncher.goSettingsFragment(activity, null);
                } else {
                    Bundle message = new Bundle();
                    message.putInt("RECEIVER_ID", broadcastResourceId);
                    LoadFinishReceiver.sendBroadcast(activity, message);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        layout.navigationMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nextBroadcastResourceId = (int) view.getTag();
                layout.changeNavigationStatus();
            }
        });

        choiceFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (layout.isBackLocking()) {
            ShowLog.d("Check back button is locking status in layout to avoid crash by changing fragments in high speed.");
            return;
        }
        if (layout.isBackListener()) {
            ShowLog.d("Execute layout back button's onClick function if exists.");
            layout.executeBackListener();
            return;
        }
        if (manager.getBackStackEntryCount() > 0) {
            ShowLog.d("Execute fragment's popBackStack if layout back button is hiding or it's onClickListener is null");
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
            int titleResource = R.string.main_activity_fragment_health;
            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.showNavigation();
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
            layout.topBar.setBackgroundColor(primary);
        }
    };

    private InitFragment.Task showHealthDetailFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            int titleResource = R.string.main_activity_fragment_health_detail;
            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_osd_health;
            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(titleColor);
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
            int titleResource = R.string.main_activity_fragment_mon_health;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_notification;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showNotificationDetailFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            CephNotificationConstant.StatusConstant statusConstant = new CephNotificationConstant.StatusConstant(activity);
            int titleColor = statusConstant.getStatusColorGroup().get(arg.getInt("0"));
            int titleResource = R.string.main_activity_fragment_notification_detail;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(titleColor);
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
            int titleResource = R.string.main_activity_fragment_host_health;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_pg_status;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_usage_status;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_pool_iops;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
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
            int titleResource = R.string.main_activity_fragment_pool_list;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.topBar.setBackgroundColor(secondary);
            layout.setSelected(titleResource);
            broadcastResourceId = titleResource;
            nextBroadcastResourceId = broadcastResourceId;
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showHostDetailFragment = new InitFragment.Task() {
        @Override
        public void action(final Bundle arg) {
            String hostName = arg.getString("0");

            setTitle(hostName);
            layout.hideAllComponent();
            layout.showTab();
            layout.topBar.setBackgroundColor(secondary);
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity, FragmentLauncher.THIRD_FRAGMENT_LAYER);
                }
            });

            layout.addTab(getString(R.string.main_activity_fragment_tab_summary), null, new OnTabChangeListener() {
                @Override
                public void onChange(int index, String name, Object tag) {
                    FragmentLauncher.goHostDetailSummaryFragment(MainActivity.this, arg);
                }
            });
            layout.addTab(getString(R.string.main_activity_fragment_tab_all_cpus), null, new OnTabChangeListener() {
                @Override
                public void onChange(int index, String name, Object tag) {
                    FragmentLauncher.goHostDetailAllCpusFragment(MainActivity.this, arg);
                }
            });
            layout.addTab(getString(R.string.main_activity_fragment_tab_iops), null, new OnTabChangeListener() {
                @Override
                public void onChange(int index, String name, Object tag) {
                    FragmentLauncher.goHostDetailIopsFragment(MainActivity.this, arg);
                }
            });
        }
    };

    private InitFragment.Task showHostDetailSummaryFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
        }
    };

    private InitFragment.Task showHostDetailAllCpusFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
        }
    };

    private InitFragment.Task showHostDetailIopsFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
        }
    };

    private InitFragment.Task showSettingsFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            int titleResource = R.string.main_activity_option_setting;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showAlertTriggersFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            int titleResource = R.string.settings_alert_alert_triggers_title;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };

    private InitFragment.Task showTimePeriodFragment = new InitFragment.Task() {
        @Override
        public void action(Bundle arg) {
            int titleResource = R.string.settings_alert_alert_triggers_title;

            setTitle(getResources().getString(titleResource));
            layout.hideAllComponent();
            layout.showBack(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentLauncher.backFragment(activity);
                }
            });
        }
    };
}