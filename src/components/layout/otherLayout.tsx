import React from 'react'

interface OtherLayoutProps {
  children: React.ReactNode
}

const OtherLayout = ({ children }: OtherLayoutProps) => {
  return (
    <div>
      <p>다른</p>
      {children}
      <p>레이아웃!</p>
    </div>
  )
}

export default OtherLayout
