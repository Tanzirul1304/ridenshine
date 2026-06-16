export default function Message({ type='info', children }) { return children ? <div className={`message ${type}`}>{children}</div> : null }
