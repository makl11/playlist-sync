Platforms
---

# Adding a new Platform

- Add your Platform to the [`SupportedPlatform`](./Platform.kt) enum
- Create a new class that implements the [`Platform`](./Platform.kt) interface in this package (`platforms`)
    - Make sure to override the `platform` field with the corresponding [`SupportedPlatform`](./Platform.kt) for your
      platform
