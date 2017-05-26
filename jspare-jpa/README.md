# Jspare JPA

Integrate Jspare with JPA using Spring Data Framework.

| Vertx Jspare Jpa | Hibernate    | Spring Data JPA |
|---------------|--------------|-----------------|
| 3.0.0        | 5.2.7.Final  | 1.11.0.RELEASE  |

## Configuration

### Maven Dependecy

```xml
  <dependency>
      <groupId>org.jspare.jpa</groupId>
      <artifactId>jspare-jpa</artifactId>
      <version>${current.version}</version>
  </dependency>
```

### PersistenceUnit Configuration

To configure JPA PersistenceUnit we use the PersistenceUnitProvider component. The PersistenceUnitProvider is responsible for handling all instances of an EntityManagerFactory created in the application.

For the parameterization and configuration of a PersistenceUnit, the [PersistenceOptions] object (src / main / asciidoc / dataobjects.adoc) was used. Example:

```json
"persistence": {
  "url": "jdbc:sqlserver://127.0.0.1:1433;databaseName=ssm_portal",
  "driverClass": "com.microsoft.sqlserver.jdbc.SQLServerDriver",
  "dialect": "org.hibernate.dialect.SQLServer2008Dialect",
  "username": "sa",
  "password": "12345678"
}
```

```java
PersistenceOptions options = new PersistenceOptions();
options.setUrl("jdbc:sqlserver://127.0.0.1:1433;databaseName=ssm_portal")
       .setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver")
       .setDialect("org.hibernate.dialect.SQLServerDialect")
       .setUsername("sa")
       .setPassword("12345678")
       .setAnnotatedClasses(Arrays.asList("foo.package.with.entities"))
```

** Creating PersistenceUnit **

```java
my(PersistenceUnitProvider.class).create(new PersistenceOptions(json), this::onLoadPersistence);
```

### Repositories and DI

We use JPA Spring Data as the framework for JPA manipulation and abstraction. All conventions and practices should be followed by official documentation. [Documentation] (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/).

For integration with the framework the control inversion can be performed in the following ways:

* @PersistenceUnit (DI JPA) - Injects the instance of the EntityManagerFactory provided by PersistenceUnitProvider
* @PersistenceContext (DI JPA) Injects a new EntityManager instance provided by EntityManagerFactory
* @RepositoryInject (DI SSM Framework) Injects a new instance of a repository implemented by SpringDataJpa

Repository Sample

** JPA  Entity**

```java
@Entity
public class User extends IdentityObject {
  
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected long id;
  
  @Column
  protected String username;
  
  /* Getters and Setters ...*/
}
```

** Repository **

```java
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {
}
```

** Components using Repository **

```java
@Component
public interface UserService {

  void save(User user);
}

public class UserServiceImpl implements UserService {
  
  @RepositoryInject
  private UserRepository repository;
  
  @Override
  public void save(User user){
    
    repository.save(user);
  }
}
```

** Handling PersistenceContext **

```java
public class UserServiceImpl implements UserService {
  
  @PersistenceContext
  private EntityManager em;
  
  @Override
  public void save(User user){

    em.getTransaction().begin();
    em.persist(user);
    em.getTransaction().commit();
    em.close();
  }
}
```