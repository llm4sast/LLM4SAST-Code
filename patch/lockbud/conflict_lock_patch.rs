pub struct PrivateStateStorage {
	private_state_db: Arc<PrivateStateDB>,
	requests: RwLock<Vec<StateRequest>>,
	syncing_hashes: RwLock<HashMap<H256, Instant>>,
}

pub fn add_request(&self, request_type: RequestType, request_hashes: HashSet<H256>) -> Vec<H256> {
    let request = StateRequest {
        request_type: request_type,
        request_hashes: request_hashes.clone(),
        state: RequestState::Syncing,
    };
    let mut hashes = self.syncing_hashes.write();
    let mut requests = self.requests.write();
    requests.push(request);
    let mut new_hashes = Vec::new();
    for hash in request_hashes {
        if hashes.insert(hash, Instant::now() + Duration::from_millis(MAX_REQUEST_SESSION_DURATION)).is_none() {
            new_hashes.push(hash);
        }
    }
    new_hashes
}