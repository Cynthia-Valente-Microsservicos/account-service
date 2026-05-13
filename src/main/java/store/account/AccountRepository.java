package store.account;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountModel, String> {

    public Optional<AccountModel> findByEmailAndPasswordSha256(String email, String sha256);

}