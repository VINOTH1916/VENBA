# VENBA

> A Tamil prosody learning and analysis project for studying **Venba** poetry, its *asai*, *seer*, *mathirai*, and *ner/nirai* patterns.

## Overview

VENBA combines two related interfaces:

- An Android application package named `com.javaproject.venbakural`.
- A standalone browser-based Tamil Venba analyzer in [`index.html`](index.html).

The project is intended for students, teachers, and Tamil-language enthusiasts who want to enter Tamil poetry and inspect its metrical structure. The analyzer displays mathirai values, classifies syllable groups as **நேர்** or **நிரை**, produces Venba word patterns such as **தேமா**, **புளிமா**, **கூவிளம்**, and **கருவிளம்**, and reports whether the input matches the implemented Venba rules.

## Features

- Tamil-language explanation of Venba grammar and terminology.
- Browser-based analysis without a backend server.
- Tamil character and compound-character handling.
- Mathirai mapping for Tamil letters and pulli endings.
- நேர் / நிரை classification.
- Venba validation based on the generated binary metrical segments.
- Display of the corresponding வாய்பாடு names:
  - தேமா
  - புளிமா
  - கூவிளம்
  - கருவிளம்
  - தேமாங்காய்
  - புளிமாங்காய்
  - கூவிளங்காய்
  - கருவிளங்காய்
  - நாள், மலர், காசு, பிறப்பு
- Android build artifacts, including a debug APK and Android App Bundle, are present under `app/build/outputs`.

## How the analyzer works

The browser implementation in `index.html` follows this pipeline:

1. `initializeMathiraiMap()` assigns mathirai values to the supported Tamil letters.
2. `splitToSyllables()` and `isTamilCompound()` separate Tamil characters and vowel signs.
3. `getMathiraiValues()` converts the input into numeric mathirai values.
4. `applyNiraiNerRules()` groups those values into நேர் and நிரை patterns.
5. `isVenba()` checks the resulting pattern against the implemented Venba constraints.
6. `printVenbaWords()` converts binary patterns into traditional வாய்பாடு names.
7. `analyzePoetry()` updates the result panels in the page.

The validation logic is an implementation of the rules encoded in the project; it should be treated as an educational analyzer rather than a complete linguistic proof engine.

## Repository structure

```text
.
├── index.html                  # Standalone Tamil Venba analyzer and reference material
├── app/
│   └── build/
│       ├── outputs/apk/        # Generated debug APKs
│       ├── outputs/bundle/     # Generated debug Android App Bundle
│       ├── intermediates/      # Android/Gradle generated build outputs
│       └── outputs/logs/        # Manifest merger reports
├── .gradle/                    # Checked-in Gradle cache and generated accessors
├── .idea/                      # Android Studio/IntelliJ project metadata
└── local.properties            # Local Android SDK path; should not be committed
```

> **Repository maintenance note:** The current repository snapshot contains generated Android/Gradle output and `local.properties`, but does not contain the normal Android source and Gradle configuration files expected in a clean Android Studio project. For a maintainable project, commit `app/src`, `settings.gradle`, the root/module Gradle files, and the Gradle wrapper, while removing generated `build/`, `.gradle/`, and machine-specific `local.properties` files from version control.

## Android project details

The generated Android manifest identifies the application as:

- **Package:** `com.javaproject.venbakural`
- **Minimum SDK:** 24
- **Target SDK:** 34
- **Version:** 1.0 (version code 1)
- **Launcher activity:** `MainActivity`

The generated build metadata shows AndroidX and Material-based dependencies, including Activity, AppCompat, ConstraintLayout, Material Components, AndroidX Test, JUnit, and Espresso. The manifest also declares audio and storage-related permissions, so those permissions should be reviewed before publishing the application.

## Running the web analyzer

The web analyzer is a self-contained HTML file and does not require a server or package installation.

```bash
git clone https://github.com/VINOTH1916/VENBA.git
cd VENBA

# Option 1: open index.html directly in a browser
# Option 2: serve it locally
python -m http.server 8000
```

Then open `http://localhost:8000/index.html` and:

1. Enter Tamil text in the input box.
2. Select **Analyze**.
3. Review the mathirai values, நேர்/நிரை analysis, binary segments, and Venba result.

## Android development

The repository includes generated Android build outputs, but the source/build configuration required for a reproducible Android build is incomplete in the current snapshot. If the missing Android source and Gradle files are restored, open the project in Android Studio and use the standard Android build tasks:

```bash
./gradlew assembleDebug
./gradlew test
./gradlew connectedAndroidTest
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

## Testing

The generated build tree contains Android unit-test and instrumentation-test outputs. Add or restore the corresponding source test files and Gradle wrapper before relying on the commands above in a fresh checkout.

## Known limitations and review notes

- The repository currently mixes a browser implementation with generated Android output.
- `local.properties` contains a developer-specific Windows SDK path and should be removed from source control.
- Generated directories such as `.gradle/` and `app/build/` make the repository unnecessarily large and are not suitable as primary source artifacts.
- The browser analyzer uses global mutable state (`j` and `nernirai`); reset that state at the beginning of each analysis to avoid incorrect results after multiple analyses.
- Unsupported or unrecognized Tamil characters are represented internally with `-1.0` and can be treated as word boundaries, which may affect validation.
- The HTML document contains some malformed/duplicated styling and visible encoding artefacts; normalize the file to UTF-8 before further development.
- Add automated test cases for valid Venba, invalid line counts, invalid final seer patterns, punctuation, spaces, and repeated analyses.

## Contributing

1. Create a feature branch.
2. Keep generated files and machine-specific configuration out of commits.
3. Make changes in the browser implementation or restored Android source tree.
4. Add tests for changes to the mathirai and metrical rules.
5. Verify the analyzer with representative Tamil examples.
6. Open a pull request describing the rule or user-interface change.

## License

No license file is currently present. Add an explicit open-source license before distributing or reusing this project.

## Project status

VENBA is an educational prototype for Tamil Venba analysis. The browser analyzer is directly runnable from `index.html`; the Android portion should be cleaned and completed before it is treated as a reproducible production application.
