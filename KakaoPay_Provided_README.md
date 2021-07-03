## 실행 및 제출 시 주의사항

- Port 번호는 **PORT 환경 변수** 또는 **5000** 을 사용해야 합니다.

## Procfile

programmers의 과제 테스트는 내부적으로 `Procfile` **파일**을 통해 제출한 코드를 실행합니다. 이 Procfile에는 앱 프로세스를 실행하는 명령이 지정되어 있어야 합니다.

Procfile은 프로세스 유형을 개별 라인에 선언합니다. 각 라인은 다음 형식으로 되어 있어야 합니다.

```
<프로세스 유형>: <명령>
```

`Procfile` 을 사용하여 다음과 같은 프로세스 유형을 선언 할 수 있습니다.

- **web**: 웹 서버 프로세스를 실행하는 명령을 지정할 수 있습니다. 예를 들면 spring boot 에서는 다음과 같이 정의 할 수 있습니다.

  ```
  web: java -Dserver.port=$PORT $JAVA_OPTS -jar build/libs/*.jar
  ```

  > 참고: Maven인 경우에는 다음과 같이 정의 할 수 있습니다.
  >
  > ```
  > web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar --port $PORT target/*.war
  > ```

- **build**: 웹 서버를 빌드하기 위한 명령을 지정할 수 있습니다. 예를들면 maven인 경우 다음과 같이 정의할 수 있습니다.

```
build: mvn install
```

- **migrate**: programmers의 과제 테스트는 코드를 실행 시, 매번 새로운 데이터베이스 서버를 실행합니다. 따라서 데이터베이스에 테이블을 생성하는 명령을 이 유형에 정의 해야합니다.

  ```
  migrate: ./gradlew flywayMigrate
  ```

  > 참고: `flyway` 외의 다른 plugin을 이용하고자 하는 경우, 마이그레이션 cli 명령을 `migrate:` 에 작성해 주어야 합니다.

  > **주의!**: migrate 유형은 반드시 정의되어 있어야 합니다. 만약 서버 실행시 자동으로 db가 sync되어 이 명령이 필요없다면 다음과 같이 정의해주세요.
  >
  > ```
  > migrate: /bin/true
  > ```

- **seed**: 초기 데이터를 데이터베이스에 입력하기 위한 명령을 지정합니다. migrate 이후에 실행됩니다.

- 이외에 다른 프로세스 유형은 지원하지 않습니다.
