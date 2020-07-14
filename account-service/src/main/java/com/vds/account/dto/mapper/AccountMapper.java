package com.vds.account.dto.mapper;

import com.vds.account.domain.Account;
import com.vds.account.domain.User;
import com.vds.account.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
/**
 *  Mapstruct interface to perform mapping of incoming DTOs in controller layer into Account model entity.
 **/
@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account accountDtoToAccount(AccountDto accountDto);

    @Mapping(source = "email", target = "username")
    User accountDtoToUser(AccountDto accountDto);

}
