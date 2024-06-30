<div align="center">
 <h1>KPayPal</h1>
 <div>
  <a href="https://discord.worldmandia.cc/">
   <img alt="Discord" src="https://img.shields.io/badge/Discord-WorldMandia-738bd7.svg?style=square" />
  </a>
 </div>
 <br>
</div>

KPayPal is a [coroutine-based](https://kotlinlang.org/docs/reference/coroutines-overview.html), implementation
of the PayPal API, written 100% in [Kotlin](https://kotlinlang.org/).

## PayPal Rest api

This is a table of what will be added or is already in this library.

| API Name              | Version | Links                                                                             | Status               |
|-----------------------|---------|-----------------------------------------------------------------------------------|----------------------|
| Add Tracking          | v1      | [API Reference](https://developer.paypal.com/docs/api/tracking/v1/)               | Coming soon... 🚧 🚧 |
| Catalog Products      | v1      | [API Reference](https://developer.paypal.com/docs/api/catalog-products/v1/)       | Coming soon... 🚧 🚧 |
| Disputes              | v1      | [API Reference](https://developer.paypal.com/docs/api/customer-disputes/v1/)      | Coming soon... 🚧 🚧 |
| Invoices              | v1      | [API Reference](https://developer.paypal.com/docs/api/invoicing/v1/)              | Deprecated... 📛 📛  |
| Invoices              | v2      | [API Reference](https://developer.paypal.com/docs/api/invoicing/v2/)              | Coming soon... 🚧 🚧 |
| Orders                | v1      | [API Reference](https://developer.paypal.com/docs/api/orders/v1/)                 | Deprecated... 📛 📛  |
| Orders                | v2      | [API Reference](https://developer.paypal.com/docs/api/orders/v2/)                 | In dev... ✅          |
| Partner Referrals     | v1      | [API Reference](https://developer.paypal.com/docs/api/partner-referrals/v1/)      | Deprecated... 📛 📛  |
| Partner Referrals     | v2      | [API Reference](https://developer.paypal.com/docs/api/partner-referrals/v2/)      | Coming soon... 🚧 🚧 |
| Payment Experience    | v1      | [API Reference](https://developer.paypal.com/docs/api/payment-experience/v1/)     | Coming soon... 🚧 🚧 |
| Payment Method Tokens | v3      | [API Reference](https://developer.paypal.com/docs/api/payment-tokens/v3/)         | Coming soon... 🚧 🚧 |
| Payments              | v1      | [API Reference](https://developer.paypal.com/docs/api/payments/v1/)               | Deprecated... 📛 📛  |
| Payments              | v2      | [API Reference](https://developer.paypal.com/docs/api/payments/v2/)               | Coming soon... 🚧 🚧 |
| Payouts               | v1      | [API Reference](https://developer.paypal.com/docs/api/payments.payouts-batch/v1/) | Coming soon... 🚧 🚧 |
| Subscriptions         | v1      | [API Reference](https://developer.paypal.com/docs/api/subscriptions/v1/)          | Coming soon... 🚧 🚧 |
| Transaction Search    | v1      | [API Reference](https://developer.paypal.com/docs/api/transaction-search/v1/)     | Coming soon... 🚧 🚧 |
| Webhooks Management   | v1      | [API Reference](https://developer.paypal.com/docs/api/webhooks/v1/)               | Coming soon... 🚧 🚧 |

## Installation

Replace `{version}` with the latest version number.

For Snapshots replace `{version}` with `{branch}-SNAPSHOT`

e.g: `feature-amazing-thing-SNAPSHOT` for the branch `feature/amazing-thing`

For Snapshots for the branch `master` replace `{version}` with `{nextPlannedApiVersion}-SNAPSHOT` (see `nextPlannedApiVersion`
in [`gradle.properties`](../gradle.properties))

### Gradle (Kotlin)

```kotlin
repositories {
    mavenCentral()
    maven("https://repo.worldmandia.cc/releases")
    // Snapshots Repository (Optional):
    maven("https://repo.worldmandia.cc/snapshots")
}

dependencies {
    implementation("cc.worldmandia:kpaypal-api:{version}")
}
```

### Gradle (Groovy)

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://repo.worldmandia.cc/releases"
    }
    // Snapshots Repository (Optional):
    maven {
        url "https://repo.worldmandia.cc/snapshots"
    }
}

dependencies {
    implementation("cc.worldmandia:kpaypal-api:{version}")
}
```

### Maven

##### Repository:

```xml

<repository>
    <id>snapshots-repo</id>
    <url>https://repo.worldmandia.cc/releases</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
</repository>
```

##### Snapshots Repository (Optional):

```xml

<repository>
    <id>snapshots-repo</id>
    <url>https://repo.worldmandia.cc/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

---

```xml

<dependency>
    <groupId>cc.worldmandia</groupId>
    <artifactId>kpaypal-api-jvm</artifactId>
    <version>{version}</version>
</dependency>
```

## FAQ

## Will you support other kotlin targets

We currently support Kotlin/JVM, there are no plans to add support for other targets, but you are welcome to do so.