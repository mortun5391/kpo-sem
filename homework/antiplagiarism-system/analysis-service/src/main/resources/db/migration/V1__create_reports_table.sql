CREATE TABLE IF NOT EXISTS reports (
    id BIGSERIAL PRIMARY KEY,
    work_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    similarity_score DOUBLE PRECISION,
    is_plagiarized BOOLEAN DEFAULT FALSE,
    analysis_details JSONB,
    error_message TEXT,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_reports_work_id ON reports(work_id);
CREATE INDEX IF NOT EXISTS idx_reports_status ON reports(status);
CREATE INDEX IF NOT EXISTS idx_reports_plagiarized ON reports(is_plagiarized);
CREATE INDEX IF NOT EXISTS idx_reports_similarity_score ON reports(similarity_score);
CREATE INDEX IF NOT EXISTS idx_reports_created_at ON reports(created_at);