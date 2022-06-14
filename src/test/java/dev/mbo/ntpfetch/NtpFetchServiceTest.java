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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NtpFetchServiceTest {

    @Test
    void calculateTimeDifference() throws IOException {
        var service = new NtpFetchService(10_000);
        var result = service.calculateTimeDifference("at.pool.ntp.org");
        assertThat(result).isNotNull();
        assertThat(result.getSystemNtpTime()).isNotNull();
        assertThat(result.getRemoteNtpTime()).isNotNull();
        assertThat(result.getDiff()).isNotNull();

        System.out.println("############################################################");
        System.out.println("# System ntp time:\t" + result.getSystemNtpTime());
        System.out.println("# Atomic ntp time:\t" + result.getRemoteNtpTime());
        System.out.println("# Time diff in ms:\t" + result.getDiff());
        System.out.println("############################################################");
    }

    @Test
    void calculateTimeDifferenceBadHost() {
        Assertions.assertThrows(IOException.class, () ->
                new NtpFetchService(10_000)
                        .calculateTimeDifference("daskjdkjaskldjklasjdklasd.asdasd")
        );
    }

    @Test
    void calculateTimeDifferenceBadHost2() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NtpFetchService(10_000)
                        .calculateTimeDifference("")
        );
    }

    @Test
    void calculateTimeDifferenceBadHost3() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new NtpFetchService(10_000)
                        .calculateTimeDifference(null)
        );
    }

    @Test
    void calculateTimeDifferenceBadTimeout() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new NtpFetchService(-1));
    }
}