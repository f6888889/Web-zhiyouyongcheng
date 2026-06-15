package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.TicketType;
import com.zhiyou.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/list")
    public R<List<TicketType>> list() {
        return R.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public R<TicketType> getById(@PathVariable Long id) {
        return R.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/spot/{spotId}")
    public R<List<TicketType>> getBySpotId(@PathVariable Long spotId) {
        return R.ok(ticketService.getTicketsBySpotId(spotId));
    }

    @PostMapping
    public R<Void> create(@RequestBody TicketType ticketType) {
        boolean success = ticketService.createTicket(ticketType);
        return success ? R.ok() : R.fail("创建失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody TicketType ticketType) {
        boolean success = ticketService.updateTicket(ticketType);
        return success ? R.ok() : R.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = ticketService.deleteTicket(id);
        return success ? R.ok() : R.fail("删除失败");
    }
}
