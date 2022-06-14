/*
 * Copyright 2022 mbo.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.mbo.ntpfetch;

import java.time.Instant;
import java.util.Objects;

/**
 * Data holding class for ntp time calculation.
 *
 * @author manuel
 */
public class NtpTimeDiff {

    /**
     * Local time.
     */
    private final Instant systemNtpTime;

    /**
     * Remote time.
     */
    private final Instant remoteNtpTime;

    /**
     * Time difference in milliseconds.
     */
    private final long diff;

    public NtpTimeDiff(long systemNtpTime, long remoteNtpTime) {
        this.systemNtpTime = Instant.ofEpochMilli(systemNtpTime);
        this.remoteNtpTime = Instant.ofEpochMilli(remoteNtpTime);
        this.diff = systemNtpTime - remoteNtpTime;
    }

    public Instant getSystemNtpTime() {
        return systemNtpTime;
    }

    public Instant getRemoteNtpTime() {
        return remoteNtpTime;
    }

    public long getDiff() {
        return diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NtpTimeDiff that = (NtpTimeDiff) o;
        return diff == that.diff &&
                Objects.equals(systemNtpTime, that.systemNtpTime) &&
                Objects.equals(remoteNtpTime, that.remoteNtpTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemNtpTime, remoteNtpTime, diff);
    }

    @Override
    public String toString() {
        return "NetFetchResult{" +
                "systemNtpTime=" + systemNtpTime +
                ", atomicNtpTime=" + remoteNtpTime +
                ", diff=" + diff +
                '}';
    }
}
