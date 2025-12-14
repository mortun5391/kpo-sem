-- file-storing-service/src/main/resources/db/migration/V1__create_works_table.sql
CREATE TABLE IF NOT EXISTS works (
    id BIGSERIAL PRIMARY KEY,
    student_id VARCHAR(255) NOT NULL,
    assignment_id VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(500) NOT NULL,
    stored_file_name VARCHAR(255) NOT NULL UNIQUE,
    file_size BIGINT,
    mime_type VARCHAR(100),
    file_path TEXT,
    upload_date TIMESTAMP,
    status VARCHAR(50) DEFAULT 'UPLOADED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    
    CONSTRAINT chk_status CHECK (status IN ('UPLOADED', 'PROCESSING', 'ANALYZING', 'COMPLETED', 'FAILED'))
);

CREATE INDEX IF NOT EXISTS idx_works_student_id ON works(student_id);
CREATE INDEX IF NOT EXISTS idx_works_assignment_id ON works(assignment_id);
CREATE INDEX IF NOT EXISTS idx_works_status ON works(status);
CREATE INDEX IF NOT EXISTS idx_works_upload_date ON works(upload_date);