package com.resourcelibrary.model.network.api.ceph.object;

/**
 * Created by User on 2015/6/29.
 */
public class CephStaticValue {
    public static final String ACTIVE = "active";
    public static final String CLEAN = "clean";
    public static final String CREATING = "creating";
    public static final String RELAYING = "replaying";
    public static final String SPLITTING = "splitting";
    public static final String SCRUBBING = "scrubbing";
    public static final String DEGRADED = "degraded";
    public static final String REPAIR = "repair";
    public static final String RECOVERING = "recovering";
    public static final String BACKFILL = "backfill";
    public static final String WAIT_BACKFILL = "wait-backfill";
    public static final String REMAPPED = "remapped";
    public static final String INCONSISTENT = "inconsistent";
    public static final String DOWN = "down";
    public static final String PEERING = "peering";
    public static final String INCOMPLETE = "incomplete";
    public static final String STALE = "stale";

    public static final String[] PG_STATUS = {
            ACTIVE, CLEAN, CREATING, RELAYING, SPLITTING, SCRUBBING, DEGRADED, REPAIR,
            RECOVERING, BACKFILL, WAIT_BACKFILL, REMAPPED, INCONSISTENT, DOWN, PEERING,
            INCOMPLETE, STALE,
    };

    public static final String[] PG_STATUS_OK = {
            ACTIVE, CLEAN
    };
    public static final String[] PG_STATUS_WARN = {
            CREATING, RELAYING, SPLITTING, SCRUBBING, DEGRADED, REPAIR,
            RECOVERING, BACKFILL, WAIT_BACKFILL, REMAPPED,
    };

    public static final String[] PG_STATUS_CRITICAL = {
            INCONSISTENT, DOWN, PEERING, INCOMPLETE, STALE,
    };
}
