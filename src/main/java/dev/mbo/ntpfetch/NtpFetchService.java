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

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Simple service to fetch remote ntp time from a given server and calculate diff of local time compared to remote time.
 *
 * @author manuel
 */
public class NtpFetchService {

    private final NTPUDPClient client;

    public NtpFetchService(final int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout has to be positive");
        }
        client = new NTPUDPClient();
        client.setDefaultTimeout(timeout);
    }

    /**
     * Calculates the time difference between local machine and given ntp server.
     *
     * @return NtpTimeDiff with parsed timestamps and time diff.
     * @throws IOException If the server isn't reachable or sends invalid response.
     */
    public NtpTimeDiff calculateTimeDifference(final String serverName) throws IOException {
        if (null == serverName || serverName.isBlank()) {
            throw new IllegalArgumentException("serverName must not be blank or null");
        }
        final var inetAddress = InetAddress.getByName(serverName);
        final TimeInfo timeInfo = client.getTime(inetAddress);
        timeInfo.computeDetails();
        var offset = 0L;
        if (timeInfo.getOffset() != null) {
            offset = timeInfo.getOffset();
        }

        // This system NTP time
        final TimeStamp systemNtpTime = TimeStamp.getCurrentTime();
        // Calculate the remote server NTP time
        final TimeStamp remoteNtpTime = TimeStamp.getNtpTime(System.currentTimeMillis() + offset);

        return new NtpTimeDiff(
                systemNtpTime.getTime(),
                remoteNtpTime.getTime()
        );
    }

}
