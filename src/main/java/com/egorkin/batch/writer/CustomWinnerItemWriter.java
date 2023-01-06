package com.egorkin.batch.writer;

import com.egorkin.model.datamodel.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;


@Slf4j
public class CustomWinnerItemWriter implements ItemWriter<Client> {

    @Override
    public void write(Chunk<? extends Client> chunk) throws Exception {
        log.info("CustomWinnerItemWriter write");
        log.info(chunk.getItems().toString());
    }

}
