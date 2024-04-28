package com.wesleyegberto.usersauditor.history;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends CrudRepository<UserDataHistory, Long> {
}
