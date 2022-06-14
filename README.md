# ntpfetch

This project provides a simple service to calculate ntp times.

## Sample

Taken from the tests.

```java
class NtpFetchServiceTest {

    void calculateTimeDifference() {
        var service = new NtpFetchService(10_000);
        var result = service.calculateTimeDifference("at.pool.ntp.org");

        System.out.println("############################################################");
        System.out.println("# System ntp time:\t" + result.getSystemNtpTime());
        System.out.println("# Atomic ntp time:\t" + result.getRemoteNtpTime());
        System.out.println("# Time diff in ms:\t" + result.getDiff());
        System.out.println("############################################################");
    }
}
```