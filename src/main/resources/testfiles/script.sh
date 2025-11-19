#!/bin/bash

# Это комментарий в bash скрипте
echo "Starting backup script..."

# Переменные
BACKUP_DIR="/var/backups"
LOG_FILE="/var/log/backup.log"

# Функция создания бэкапа
create_backup() {
    local source_dir=$1
    local backup_name=$2

    # Проверяем существование директории
    if [ ! -d "$source_dir" ]; then
        echo "Error: Source directory does not exist"
        return 1
    fi

    # Создаем бэкап
    tar -czf "$BACKUP_DIR/$backup_name.tar.gz" "$source_dir"

    # Логируем результат
    if [ $? -eq 0 ]; then
        echo "Backup created successfully: $backup_name.tar.gz"
    else
        echo "Backup failed for: $backup_name"
    fi
}

# Основная логика
main() {
    echo "Backup process started at $(date)" >> "$LOG_FILE"

    # Создаем бэкапы
    create_backup "/home/user/documents" "documents_backup"
    create_backup "/etc" "config_backup"

    echo "Backup process completed at $(date)" >> "$LOG_FILE"
}

# Вызываем основную функцию
main "$@"