package com.hw.shared.idempotent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.shared.IdGenerator;
import com.hw.shared.idempotent.command.AppCreateChangeRecordCommand;
import com.hw.shared.idempotent.model.ChangeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppChangeRecordApplicationService {
    @Autowired
    private IdGenerator idGenerator2;
    @Autowired
    private ChangeRepository changeHistoryRepository;
    @Autowired
    private ObjectMapper om2;

    public void create(AppCreateChangeRecordCommand command) {
        long id = idGenerator2.getId();
        ChangeRecord changeRecord = ChangeRecord.create(id, command, om2);
        changeHistoryRepository.save(changeRecord);
    }

}
