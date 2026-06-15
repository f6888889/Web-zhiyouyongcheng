package com.zhiyou.service;

import com.zhiyou.model.TicketType;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TicketService {

    private final JsonDataRepository repository;

    public TicketService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "ticket.json";

    public List<TicketType> getAllTickets() {
        return repository.selectList(FILE_NAME, TicketType.class);
    }

    public TicketType getTicketById(Long id) {
        return repository.selectById(FILE_NAME, id, TicketType.class);
    }

    public List<TicketType> getTicketsBySpotId(Long spotId) {
        return repository.selectListByField(FILE_NAME, TicketType.class, "spotId", spotId);
    }

    public boolean createTicket(TicketType ticketType) {
        ticketType.setCreateTime(LocalDateTime.now());
        return repository.insert(FILE_NAME, ticketType);
    }

    public boolean updateTicket(TicketType ticketType) {
        return repository.update(FILE_NAME, ticketType);
    }

    public boolean deleteTicket(Long id) {
        return repository.delete(FILE_NAME, id, TicketType.class);
    }
}
