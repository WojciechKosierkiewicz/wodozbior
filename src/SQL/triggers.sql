create trigger trg_delete_old_logs
    after insert on error_logs
    for each statement
    execute procedure delete_old_logs();