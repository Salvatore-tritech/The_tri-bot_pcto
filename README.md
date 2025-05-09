# Guida step‑by‑step

**Dal Chat Bot Base al Chatbot Skillato con Groq Cloud (Java 11)**
Classe IV – Informatica

> *Obiettivo*: partire dal progetto fornito e costruire
>
> 1. un **chat bot semplice** che inoltra i messaggi a un Large Language Model (LLM) su Groq Cloud;
> 2. un **chatbot “skillato”** che prima prova a rispondere con **skill locali** e, solo se serve, chiede aiuto al modello LLM.

---

## 0  Requisiti minimi

| Strumento      | Versione                 | Dove trovarlo                                        |
| -------------- | ------------------------ | ---------------------------------------------------- |
| **JDK**        | 11 (Temurin consigliato) | [https://adoptium.net](https://adoptium.net)         |
| **Maven**      | 3.9 +                    | [https://maven.apache.org](https://maven.apache.org) |
| **Groq Cloud** | account + API key        | [https://console.groq.com](https://console.groq.com) |
| **Git**        | qualunque                | [https://git‑scm.com](https://git‑scm.com)           |

---

## 1  Clonare il progetto di partenza

```bash
git clone https://github.com/Salvatore-tritech/The_tri-bot_pcto.git   # oppure unzip il file fornito
cd ai-challenge-bot
```

> Se hai ricevuto un archivio `.zip`, basta estrarlo: la radice del progetto è **`untitled/`**.

---

## 2  Impostare le variabili d’ambiente

1. Copia il template:

   ```bash
   cp .env.template .env
   ```

2. Apri `.env` e sostituisci i valori:

   ```properties
   GROQ_API_KEY=<la tua chiave>
   GROQ_MODEL=gpt-4o-2024-04-09   # o altro modello disponibile
   # facoltativo:
   # GROQ_BASE_URL=https://api.groq.com/openai/v1
   ```

3. **Linux/macOS**

   ```bash
   source .env
   ```

   **Windows PowerShell**

   ```powershell
   setx GROQ_API_KEY "<la tua‑key>"
   setx GROQ_MODEL "gpt-4o-2024-04-09"
   ```

---

## 3  Diamo un’occhiata alla struttura

```
src/
  main/java/it/aichallenge/
    bot/           # SimpleBot, SkillfulBot
    client/        # AIClient, GroqClient
    skills/        # interfaccia BotSkill + SkillRegistry
    config/        # AiConfig -> legge due env var
    challenge/     # Main.java: CLI di esempio
```

*`pom.xml`* include OkHttp + Jackson.

---

## 4  FASE 1 Chat Bot Base

### 4.1 Compilare e lanciare i test

```bash
mvn test          # ⚠️ salta i test d'integrazione se manca la API key
```

Se tutto è verde ➜ avanti!

### 4.2 Come funziona il giro “user → LLM”

1. `Main.java` legge l’input da console
2. passa il testo a **`SimpleBot`**
3. che chiama `GroqClient.chat(…)`
4. che fa la request HTTPS a Groq Cloud e ritorna la risposta

Apri `src/main/java/it/aichallenge/challenge/Main.java` e prova:

```bash
mvn exec:java -Dexec.mainClass="it.aichallenge.challenge.Main"
```

Digita una domanda, ad es.:

```
> Qual è la capitale del Canada?
```

---

## 5  FASE 2 Aggiungere la prima **Skill**

### 5.1 Cos’è una skill?

```java
public interface BotSkill {
    String tryReply(String userMessage);
}
```

Se ritorna `null`, la palla passa al prossimo handler.

### 5.2 Esempio: `/data`

```java
package it.aichallenge.skills;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSkill implements BotSkill {
    private static final DateTimeFormatter ITA = DateTimeFormatter.ofPattern("d MMMM uuuu");

    @Override
    public String tryReply(String msg) {
        return "/data".equalsIgnoreCase(msg.trim())
               ? "Oggi è " + LocalDate.now().format(ITA)
               : null;
    }
}
```

### 5.3 Registrare le skill

```java
SkillRegistry reg = new SkillRegistry()
        .add(new DateSkill())
        .add(msg -> "/help".equals(msg) ? "Comandi: /data, /help" : null);
/* … */
Bot smart = new SkillfulBot(reg, new GroqClient(AiConfig.load()));
```

Aggiorna `Main.java` per usare `SkillfulBot`.

### 5.4 Prova

```
> /data
Oggi è 9 maggio 2025
> Spiegami la relatività
[risposta generata da Groq LLM]
```

---

## 6  FASE 3 Personalizzare il prompt (opzionale)

Modifica `GroqClient.chat()` per inviare un *messaggio system* aggiuntivo:

```java
"messages", List.of(
   Map.of("role","system","content","Sei un assistente molto sintetico."),
   Map.of("role","user","content", prompt)
)
```

Puoi anche variare **`temperature`** (0–2).

---

## 7  FASE 4 Skill avanzate con **function calling** (extra)

1. Aggiorna il **body** della request con il campo `"tools"` secondo lo standard OpenAI.
2. Implementa un serializzatore che converte la proposta di chiamata (`tool_call`) in invocazione Java della tua funzione.
3. Usa un ciclo *“LLM ➜ tool ➜ LLM”* per restituire la risposta finale.

> Suggerimento: parti da un’API pubblica semplice, es. Open‑Meteo.

---

## 8  Debug & Trouble‑shooting

| Errore                              | Possibile causa                            |
| ----------------------------------- | ------------------------------------------ |
| `401 Unauthorized`                  | API key mancante o scaduta                 |
| `UnknownHostException api.groq.com` | Niente Internet o proxy scolastico         |
| Risposta vuota                      | Hai superato il **rate limit** o **quota** |

Usa l’opzione `-Dlogging=debug` nel `pom.xml` per vedere il JSON completo.

---

## 9  Checklist finale

* [ ] Tutti i test JUnit passano
* [ ] Il bot risponde a `/data` **senza** contattare Groq
* [ ] Per gli altri messaggi attinge al modello LLM
* [ ] README aggiornato con il tuo nome e la lista delle skill

Fatto? *Push* su GitHub e condividi il link con il docente 🚀

---

## 10  Risorse utili

* Groq Cloud docs – [https://console.groq.com/docs](https://console.groq.com/docs)
* OpenAI “Function Calling” spec – [https://platform.openai.com/docs/guides/function-calling](https://platform.openai.com/docs/guides/function-calling)
* Jackson Databind quickstart – [https://github.com/FasterXML/jackson-databind](https://github.com/FasterXML/jackson-databind)
* OkHttp recipes – [https://square.github.io/okhttp/recipes/](https://square.github.io/okhttp/recipes/)

---
