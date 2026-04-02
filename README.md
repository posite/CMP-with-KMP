# CMP with KMP — Compose Multiplatform + Kotlin Multiplatform

> Android · iOS를 단일 Kotlin 코드베이스로 개발하는 멀티플랫폼 프로젝트

---

## 📌 프로젝트 개요

**CMP-with-KMP**는 [Compose Multiplatform(CMP)](https://www.jetbrains.com/compose-multiplatform/)와 [Kotlin Multiplatform(KMP)](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)을 함께 활용하여 **Android와 iOS 앱을 하나의 Kotlin 코드베이스로 개발**하는 방법을 탐구하는 프로젝트입니다.

공유 UI 레이어(Compose Multiplatform)와 비즈니스 로직(KMP)을 최대한 재사용하면서, 플랫폼별 고유 기능은 각 플랫폼 모듈에서 처리하는 구조를 취합니다.

| 구분 | 내용 |
|------|------|
| **빌드 시스템** | Gradle (Kotlin DSL) |
| **대상 플랫폼** | Android, iOS |
| **JDK** | JDK 21 |
| **프로젝트명** | Simple |

---

## 🏗️ 프로젝트 구조

```
CMP-with-KMP/
├── composeApp/                    # KMP + CMP 공유 모듈 (핵심)
│   └── src/
│       ├── commonMain/kotlin/     # Android · iOS 공통 코드 (UI, 비즈니스 로직)
│       ├── androidMain/kotlin/    # Android 전용 구현체 (actual)
│       ├── iosMain/kotlin/        # iOS 전용 구현체 (actual)
│       └── jvmMain/kotlin/        # Desktop(JVM) 전용 구현체 (actual)
│
├── androidapp/                    # 순수 Android 전용 앱 모듈
│                                  # (composeApp의 KMP 라이브러리를 의존하는 Android 앱)
│
├── iosApp/                        # iOS 앱 진입점 (Xcode 프로젝트)
│   └── iosApp/                    # SwiftUI 코드 위치
│
├── gradle/
│   └── libs.versions.toml         # Version Catalog (의존성 중앙 관리)
│
├── build.gradle.kts               # 루트 빌드 스크립트
├── settings.gradle.kts            # 멀티 모듈 설정
└── gradle.properties              # Gradle/Android/JVM 전역 설정
```

### 모듈 역할 상세

#### `composeApp` — 공유 멀티플랫폼 모듈

모든 플랫폼이 공유하는 Compose UI와 비즈니스 로직을 담습니다. `expect/actual` 패턴을 통해 플랫폼별 구현을 분리합니다.

```
commonMain/   →  모든 플랫폼에서 실행되는 공통 코드 (UI Composable, 비즈니스 로직, 인터페이스)
androidMain/  →  Android 전용 actual 구현체 및 Android API 호출
iosMain/      →  iOS 전용 actual 구현체 및 Apple API 호출
jvmMain/      →  Desktop(JVM) 전용 actual 구현체
```

#### `androidapp` — Android 전용 앱 모듈

`composeApp`을 KMP 라이브러리로 의존하는 독립 Android 애플리케이션 모듈입니다. `androidKmpLibrary` 플러그인을 통해 `composeApp`의 공유 코드를 Android 앱에서 재사용합니다.

#### `iosApp` — iOS 앱 진입점

Xcode 프로젝트로, `composeApp`이 빌드한 KMP 프레임워크를 iOS 앱에 임베드합니다. SwiftUI 코드를 추가하거나 네이티브 iOS 기능을 연동하는 진입점 역할을 합니다.

---

## 🔧 기술 스택 및 플러그인

### 기술 스택
| 기술 스택  | 사용한 기술 |
|----------|------|
| `Language` | Kotlin |
| `UI Framework` | Compose Multiplatform(CMP) |
| `Dependency Injection` | Koin |
| `Network` | Ktor (HTTP Client) |
| `Concurrency` | Kotlin Coroutines |

### Gradle 플러그인

| 플러그인 | 용도 |
|----------|------|
| `kotlinMultiplatform` | KMP 멀티플랫폼 빌드 설정 |
| `composeMultiplatform` | Compose Multiplatform UI 공유 |
| `composeCompiler` | Compose 컴파일러 플러그인 |
| `androidApplication` | Android 앱 모듈 구성 |
| `androidKmpLibrary` | Android에서 KMP 라이브러리 사용 |
| `kotlinAndroid` | Android 전용 Kotlin 설정 |


<!-- ```properties
# JVM / Kotlin
kotlin.code.style=official
kotlin.daemon.jvmargs=-Xmx3072M
org.gradle.jvmargs=-Xmx4096M -Dfile.encoding=UTF-8
org.gradle.java.home=JDK 21

# Gradle 성능 최적화
org.gradle.configuration-cache=true   # 설정 캐시 활성화
org.gradle.caching=true               # 빌드 캐시 활성화

# Android
android.useAndroidX=true
android.nonTransitiveRClass=true
``` -->

---

## 📐 아키텍처 설계

### expect / actual 패턴

KMP의 핵심 메커니즘인 `expect/actual`을 사용하여 플랫폼별 구현을 분리합니다.

```kotlin
// commonMain — 공통 인터페이스 선언
expect fun getPlatformName(): String

// androidMain — Android 구현
actual fun getPlatformName(): String = "Android ${android.os.Build.VERSION.SDK_INT}"

// iosMain — iOS 구현 (Swift/Obj-C 브릿지)
actual fun getPlatformName(): String = UIDevice.currentDevice.systemName()
```

### 계층 구조

```
┌─────────────────────────────────────┐
│          composeApp (shared)        │
│  ┌──────────┐  ┌───────┐  ┌───────┐ │
│  │commonMain│  │iosMain│  │jvmMain│ │
│  └──────────┘  └───────┘  └───────┘ │
└─────────────────────────────────────┘
        ↙                    ↘
  androidApp               iosApp
  (Android 앱)             (iOS 앱)
```

---

## 📦 모듈 의존 관계

```
androidapp ──depends on──▶ composeApp (KMP Library)
iosApp     ──embeds──────▶ composeApp (KMP Framework)
```

`settings.gradle.kts`에서 두 모듈이 명시적으로 포함됩니다:
```kotlin
rootProject.name = "Simple"
include(":composeApp")
include(":androidapp")
```

---

## 🗂️ 개발 과정 (Development Flow)

### 1단계 — 프로젝트 초기 설정
- [KMP Wizard](https://kmp.jetbrains.com/)를 통해 Android + iOS 타겟으로 프로젝트 생성
- `composeApp` 공유 모듈 + `iosApp` 기본 구조 구성
- `libs.versions.toml` Version Catalog로 의존성 중앙 관리

### 2단계 — 멀티모듈 구조 추가
- `androidapp` 모듈 추가: `composeApp`을 KMP 라이브러리로 활용하는 Android 전용 앱 분리
- `androidKmpLibrary` 플러그인 도입으로 KMP 라이브러리 ↔ Android 앱 연동

### 3단계 — 공유 코드 작성
- `commonMain`에 플랫폼 무관 UI(Composable)와 비즈니스 로직 작성
- `expect/actual` 패턴으로 플랫폼 분기 처리

### 4단계 — 플랫폼별 구현
- `androidMain`: Android API 활용 actual 구현
- `iosMain`: Apple API 브릿지 actual 구현
- `iosApp`: SwiftUI 진입점 및 KMP 프레임워크 임베드

### 5단계 — 빌드 최적화
- Gradle Configuration Cache 및 Build Cache 활성화
- JDK 21 적용, JVM 메모리 최적화(`-Xmx4096M`)

---

## 🔗 참고 자료

- [Kotlin Multiplatform 공식 문서](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform 공식 사이트](https://www.jetbrains.com/compose-multiplatform/)
- [KMP Wizard (프로젝트 생성기)](https://kmp.jetbrains.com/)
- [Android Studio KMP 플러그인](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)
