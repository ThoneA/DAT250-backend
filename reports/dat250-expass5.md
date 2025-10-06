# Report for exercise 5

## Technical problems
I started with using Valkey, but when I tried running ./gradlew bootRun I got some errors because of Valkey, so I had to change to Redis

## Pending issues
I am not totally sure if my system is doing what I want it to do. If it is really sending caching between the systems. Because I've treid printing what happens in the terminal, but the only thing I get is this message:

2025-10-07T00:57:17.981+02:00  WARN 11261 --- [sexy] [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Ignoring exception, response committed already: org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from `StreamWriteConstraints.getMaxNestingDepth()`)
2025-10-07T00:57:17.981+02:00  WARN 11261 --- [sexy] [nio-8080-exec-7] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Document nesting depth (1001) exceeds the maximum allowed (1000, from `StreamWriteConstraints.getMaxNestingDepth()`)]

So I believe I have an infinity loop somewhere, that I need help with.