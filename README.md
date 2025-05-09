# AI Challenge Bot – Java 11 × Groq Cloud

A **minimal starter project** that shows how to call the Groq Cloud API (OpenAI‑compatible) from plain Java 11 using only:

* **OkHttp** for HTTP
* **Jackson‑Databind** for JSON
* **JUnit 5 + Mockito** for testing

The focus is *easy‑as‑possible* code for educational use in an AI programming course.

---

## 1  Prerequisites

| Tool               | Version  | Notes                                   |
| ------------------ | -------- | --------------------------------------- |
| JDK                | **11**   | Tested with Temurin 11 LTS              |
| Maven              | **3.9+** | Wrapper not included – install globally |
| Groq Cloud account | –        | Generate an API key in the dashboard    |

Set two environment variables (either in your shell or by copying `.env.template` → `.env`).

```bash
export GROQ_API_KEY="<your‑key>"
export GROQ_MODEL="gpt-4o-2024-04-09"   # or other available model
```

Optional: `GROQ_BASE_URL` (defaults to `https://api.groq.com/openai/v1`).

---

## 2  Getting Started

1. **Clone** the repository

   ```bash
   git clone https://github.com/your‑org/ai-challenge-bot.git
   cd ai-challenge-bot
   ```
2. **Run the tests** (skips smoke test if no API key)

   ```bash
   mvn test
   ```
3. **Chat from the command line**

   ```bash
   mvn exec:java -Dexec.mainClass="it.ai.challenge.Main"
   # then type messages; Ctrl‑D to quit
   ```

> **Tip**  First run may download Maven dependencies (≈ 20 MB).

---

## 3  Project Structure

```
src/
  main/java/it/ai/challenge/
    Main.java           # simple CLI
    bot/                # Bot interfaces & impls
    client/             # AIClient + GroqClient (HTTP)
    config/             # AiConfig (env loader)
    skill/              # Optional skills framework
    game/               # Optional leaderboard
  test/java/...         # Unit & smoke tests
.github/workflows/ci.yml# GitHub Actions CI (mvn test)
pom.xml                 # Maven configuration
```

---

## 4  Everyday Commands

| Task                   | Command                                                 |
| ---------------------- | ------------------------------------------------------- |
| Compile & package JAR  | `mvn -B package`                                        |
| Run unit tests         | `mvn test`                                              |
| Execute CLI            | `mvn exec:java -Dexec.mainClass="it.ai.challenge.Main"` |
| Continuous Integration | Pushed branches trigger GitHub Actions CI               |

---

## 5  Troubleshooting (quick)

* **`Missing required env var: GROQ_API_KEY`** – set the variable or copy `.env.template` to `.env`.
* **`HTTP 401`** – invalid or expired key.
* **Proxy / firewall issues** – set `-Dhttps.proxyHost` & `-Dhttps.proxyPort` JVM flags.

For more, see [`docs/troubleshooting.md`](docs/troubleshooting.md).

---

## 6  License

MIT – see `LICENSE` file.
